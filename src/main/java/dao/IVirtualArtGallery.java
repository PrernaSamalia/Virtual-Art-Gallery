package dao;

import entity.Artwork;
import entity.Gallery;

import java.util.List;
import exception.myexceptions.*;

public interface IVirtualArtGallery {
    
    // Artwork Management
    
    /**
     * Adds a new artwork to the gallery.
     * @param artwork The Artwork object to add.
     * @return True if the operation was successful, otherwise false.
     */
    boolean addArtwork(Artwork artwork);

    /**
     * Updates an existing artwork.
     * @param artwork The Artwork object with updated details.
     * @return True if the operation was successful, otherwise false.
     */
    boolean updateArtwork(Artwork artwork);

    /**
     * Removes an artwork by its ID.
     * @param artworkID The ID of the artwork to remove.
     * @return True if the operation was successful, otherwise false.
     */
    boolean removeArtwork(int artworkID);

    /**
     * Retrieves an artwork by its ID.
     * @param artworkID The ID of the artwork.
     * @return The Artwork object, or null if not found.
     */
    Artwork getArtworkById(int artworkID) throws ArtWorkNotFoundException;

    /**
     * Searches artworks based on a keyword.
     * @param keyword The keyword to search by (title or description).
     * @return A list of Artwork objects matching the keyword.
     */
    List<Artwork> searchArtworks(String keyword);
    
    // User Favorites
    
    /**
     * Adds an artwork to the user's favorites.
     * @param userId The ID of the user.
     * @param artworkId The ID of the artwork.
     * @return True if the operation was successful, otherwise false.
     */
    boolean addArtworkToFavorite(int userId, int artworkId);

    /**
     * Removes an artwork from the user's favorites.
     * @param userId The ID of the user.
     * @param artworkId The ID of the artwork.
     * @return True if the operation was successful, otherwise false.
     */
    boolean removeArtworkFromFavorite(int userId, int artworkId);

    /**
     * Retrieves all artworks favorited by a specific user.
     * @param userId The ID of the user.
     * @return A list of Artwork objects that the user has marked as favorites.
     */
    List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException;

    // Gallery Management

    /**
     * Adds a new gallery to the system.
     * @param gallery The Gallery object to add.
     * @return True if the operation was successful, otherwise false.
     */
    boolean addGallery(Gallery gallery);

    /**
     * Updates an existing gallery.
     * @param gallery The Gallery object with updated details.
     * @return True if the operation was successful, otherwise false.
     */
    boolean updateGallery(Gallery gallery);

    /**
     * Removes a gallery by its ID.
     * @param galleryID The ID of the gallery to remove.
     * @return True if the operation was successful, otherwise false.
     */
    boolean removeGallery(int galleryID);

    /**
     * Retrieves a gallery by its ID.
     * @param galleryID The ID of the gallery.
     * @return The Gallery object, or null if not found.
     */
    Gallery getGalleryById(int galleryID);

    /**
     * Searches galleries based on a keyword.
     * @param keyword The keyword to search by (name or description).
     * @return A list of Gallery objects matching the keyword.
     */
    List<Gallery> searchGalleries(String keyword);
}