package dao;

import entity.Artwork;
import entity.Gallery;
import exception.myexceptions.ArtWorkNotFoundException;
import exception.myexceptions.UserNotFoundException;
import util.DBConnUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualArtGalleryImpl implements IVirtualArtGallery {

    private static Connection connection;

    // Static block to initialize the database connection
    static {

        connection = DBConnUtil.getConnection();
    }

    /************************ Artwork Management ************************/
    
    /**
     * Adds a new artwork to the database.
     * @param artwork The artwork object containing details to be added.
     * @return true if the artwork is added successfully, false otherwise.
    */
    @Override
    public boolean addArtwork(Artwork artwork) {

        int nextId = getNextArtworkId();
        String query = "INSERT INTO Artwork (ArtworkID, Title, Description, CreationDate, Medium, ImageURL, ArtistID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, nextId);
            stmt.setString(2, artwork.getTitle());
            stmt.setString(3, artwork.getDescription());
            stmt.setDate(4, Date.valueOf(artwork.getCreationDate()));
            stmt.setString(5, artwork.getMedium());
            stmt.setString(6, artwork.getImageURL());
            stmt.setInt(7, artwork.getArtistID());
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Updates an existing artwork in the database.
     * @param artwork The artwork object with updated details.
     * @return true if the update is successful, false otherwise.
    */
    @Override
    public boolean updateArtwork(Artwork artwork) {

        String query = "UPDATE Artwork SET Title = ?, Description = ?, CreationDate = ?, Medium = ?, ImageURL = ?, ArtistID = ? WHERE ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setDate(3, Date.valueOf(artwork.getCreationDate()));
            stmt.setString(4, artwork.getMedium());
            stmt.setString(5, artwork.getImageURL());
            stmt.setInt(6, artwork.getArtistID());
            stmt.setInt(7, artwork.getArtworkID());
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Removes an artwork from the database.
     * @param artworkID The ID of the artwork to be removed.
     * @return true if the removal is successful, false otherwise.
    */
    @Override
    public boolean removeArtwork(int artworkID) {

        String query = "DELETE FROM Artwork WHERE ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, artworkID);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves an artwork by its ID.
     * @param artworkID The ID of the artwork.
     * @return The Artwork object if found, throws ArtWorkNotFoundException if not found.
    */
    @Override
    public Artwork getArtworkById(int artworkID) throws ArtWorkNotFoundException {

        String query = "SELECT * FROM Artwork WHERE ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, artworkID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Artwork(
                    rs.getInt("ArtworkID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getDate("CreationDate").toLocalDate(),
                    rs.getString("Medium"),
                    rs.getString("ImageURL"),
                    rs.getInt("ArtistID")
                );
            }
            else {

                throw new ArtWorkNotFoundException("Artwork with ID " + artworkID + " not found.");
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Searches artworks by a keyword.
     * @param keyword The keyword to search for in title, description, or medium.
     * @return A list of artworks that match the keyword.
    */
    @Override
    public List<Artwork> searchArtworks(String keyword) {

        String query = "SELECT * FROM Artwork WHERE LOWER(Title) LIKE LOWER(?) OR LOWER(Description) LIKE LOWER(?) OR LOWER(Medium) LIKE LOWER(?)";

        List<Artwork> artworks = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Artwork artwork = new Artwork(
                    rs.getInt("ArtworkID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getDate("CreationDate").toLocalDate(),
                    rs.getString("Medium"),
                    rs.getString("ImageURL"),
                    rs.getInt("ArtistID")
                );
                artworks.add(artwork);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return artworks;
    }
    
    /************************ User Favorites ************************/

    /**
     * Adds an artwork to a user's favorites.
     * @param userId The ID of the user.
     * @param artworkId The ID of the artwork.
     * @return true if added successfully, false if an error occurs.
    */
    @Override
    public boolean addArtworkToFavorite(int userId, int artworkId) {

        String query = "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, artworkId);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLIntegrityConstraintViolationException e) {

            // Handle duplicate entry error with a user-friendly message
            System.out.println("Error: The artwork is already in the user's favorites.");

            return false;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Removes an artwork from a user's favorites.
     * @param userId The ID of the user.
     * @param artworkId The ID of the artwork.
     * @return true if removed successfully, false otherwise.
    */
    @Override
    public boolean removeArtworkFromFavorite(int userId, int artworkId) {

        if (!doesUserExist(userId)) {

            System.out.println("Error: User with ID " + userId + " does not exist.");
            return false;
        }
    
        if (!doesArtworkExist(artworkId)) {

            System.out.println("Error: Artwork with ID " + artworkId + " does not exist.");
            return false;
        }

        String query = "DELETE FROM User_Favorite_Artwork WHERE UserID = ? AND ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, artworkId);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves all favorite artworks for a user.
     * @param userId The ID of the user.
     * @return A list of favorite artworks.
     * @throws UserNotFoundException if the user is not found.
    */
    @Override
    public List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException {

        String query = "SELECT a.* FROM Artwork a INNER JOIN User_Favorite_Artwork ufa ON a.ArtworkID = ufa.ArtworkID WHERE ufa.UserID = ?";

        List<Artwork> artworks = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {

                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }

            while (rs.next()) {

                Artwork artwork = new Artwork(
                    rs.getInt("ArtworkID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getDate("CreationDate").toLocalDate(),
                    rs.getString("Medium"),
                    rs.getString("ImageURL"),
                    rs.getInt("ArtistID")
                );
                artworks.add(artwork);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return artworks;
    }

    /******* Helper Methods Start *******/

    /**
     * Retrieves the next available ArtworkID.
     * @return The next ArtworkID.
    */
    public int getNextArtworkId() {

        int nextId = 1;

        String query = "SELECT MAX(ArtworkID) AS maxId FROM Artwork";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {

                nextId = rs.getInt("maxId") + 1;
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return nextId;
    }

    /**
     * Checks if a user with the specified ID exists in the database.
     * @param userId The ID of the user to check.
     * @return true if the user exists, false otherwise.
    */
    private boolean doesUserExist(int userId) {

        String query = "SELECT COUNT(*) AS count FROM User WHERE UserID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {

                return rs.getInt("count") > 0;
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Checks if an artwork with the specified ID exists in the database.
     * @param artworkId The ID of the artwork to check.
     * @return true if the artwork exists, false otherwise.
    */
    private boolean doesArtworkExist(int artworkId) {

        String query = "SELECT COUNT(*) AS count FROM Artwork WHERE ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, artworkId);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {

                return rs.getInt("count") > 0;
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
        
        return false;
    }

    /******* Helper Methods End *******/


    /************************ Gallery Management ************************/

    /**
     * Adds a new gallery to the database.
     * @param gallery The gallery object to be added.
     * @return true if the gallery is added successfully, false otherwise.
    */
    @Override
    public boolean addGallery(Gallery gallery) {

        String query = "INSERT INTO Gallery (GalleryID, Name, Description, Location, Curator, OpeningHours) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            int nextId = getNextGalleryId();
            gallery.setGalleryID(nextId);

            stmt.setInt(1, nextId);
            stmt.setString(2, gallery.getName());
            stmt.setString(3, gallery.getDescription());
            stmt.setString(4, gallery.getLocation());
            stmt.setInt(5, gallery.getCurator());
            stmt.setTime(6, Time.valueOf(gallery.getOpeningHours()));
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves the next available GalleryID.
     * @return The next GalleryID.
    */
    public int getNextGalleryId() {

        String query = "SELECT MAX(GalleryID) AS maxId FROM Gallery";

        int nextId = 1; // Default to 1 if no galleries exist

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {

                nextId = rs.getInt("maxId") + 1;
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return nextId;
    }

    /**
     * Updates an existing gallery.
     * @param gallery The gallery object with updated details.
     * @return true if the update is successful, false otherwise.
    */
    @Override
    public boolean updateGallery(Gallery gallery) {

        String query = "UPDATE Gallery SET Name = ?, Description = ?, Location = ?, Curator = ?, OpeningHours = ? WHERE GalleryID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, gallery.getName());
            stmt.setString(2, gallery.getDescription());
            stmt.setString(3, gallery.getLocation());
            stmt.setInt(4, gallery.getCurator());
            stmt.setTime(5, Time.valueOf(gallery.getOpeningHours()));
            stmt.setInt(6, gallery.getGalleryID());
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Retrieves a gallery by its ID.
     * @param galleryID The ID of the gallery.
     * @return The Gallery object if found, null otherwise.
    */
    @Override
    public Gallery getGalleryById(int galleryID) {

        String query = "SELECT * FROM Gallery WHERE GalleryID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, galleryID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Gallery(
                    rs.getInt("GalleryID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getInt("Curator"),
                    rs.getTime("OpeningHours").toLocalTime()
                );
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Removes a gallery from the database.
     * @param galleryID The ID of the gallery to be removed.
     * @return true if the removal is successful, false otherwise.
    */
    @Override
    public boolean removeGallery(int galleryID) {

        String query = "DELETE FROM Gallery WHERE GalleryID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, galleryID);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Searches for galleries by a keyword.
     * @param keyword The keyword to search for in gallery name or description.
     * @return A list of galleries that match the keyword.
    */
    @Override
    public List<Gallery> searchGalleries(String keyword) {

        String query = "SELECT * FROM Gallery WHERE Name LIKE ? OR Description LIKE ?";

        List<Gallery> galleries = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Gallery gallery = new Gallery(
                    rs.getInt("GalleryID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getInt("Curator"),
                    rs.getTime("OpeningHours").toLocalTime()
                );
                galleries.add(gallery);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return galleries;
    }
}