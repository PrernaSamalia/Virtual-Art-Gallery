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

    // Static block to initialize the connection
    static {
        connection = DBConnUtil.getConnection();
    }

    // Artwork Management
    
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeArtwork(int artworkID) {
        String query = "DELETE FROM Artwork WHERE ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artworkID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
            } else {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }
    
    // User Favorites

    @Override
    public boolean addArtworkToFavorite(int userId, int artworkId) {
        String query = "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, artworkId);
            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate entry error with a user-friendly message
            System.out.println("Error: The artwork is already in the user's favorites.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException {
        String query = "SELECT a.* FROM Artwork a INNER JOIN User_Favorite_Artwork ufa ON a.ArtworkID = ufa.ArtworkID WHERE ufa.UserID = ?";
        List<Artwork> artworks = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) { // No rows returned
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    // Helper method to get the next available ArtworkID
    public int getNextArtworkId() {
        int nextId = 1;
        String query = "SELECT MAX(ArtworkID) AS maxId FROM Artwork";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                nextId = rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    private boolean doesUserExist(int userId) {
        String query = "SELECT COUNT(*) AS count FROM User WHERE UserID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    private boolean doesArtworkExist(int artworkId) {
        String query = "SELECT COUNT(*) AS count FROM Artwork WHERE ArtworkID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, artworkId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

        // Method to add a new gallery
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get the next available GalleryID
    public int getNextGalleryId() {
        String query = "SELECT MAX(GalleryID) AS maxId FROM Gallery";
        int nextId = 1; // Default to 1 if no galleries exist
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                nextId = rs.getInt("maxId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    // Method to update an existing gallery
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a gallery by ID
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to remove a gallery by ID
    @Override
    public boolean removeGallery(int galleryID) {
        String query = "DELETE FROM Gallery WHERE GalleryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, galleryID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to search for galleries by a keyword
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return galleries;
    }
}