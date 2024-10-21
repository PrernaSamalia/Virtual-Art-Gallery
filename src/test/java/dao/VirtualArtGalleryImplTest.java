package dao;

import entity.Artwork;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DBConnUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

import exception.myexceptions.*;

public class VirtualArtGalleryImplTest {

    private VirtualArtGalleryImpl galleryService;

    @Before
    public void setUp() {
        galleryService = new VirtualArtGalleryImpl();
    }

    @After
    public void tearDown() {
        // Close the database connection after all tests are run
        DBConnUtil.dbClose();
    }

    @Test
    public void testAddArtwork() {
        Artwork newArtwork = new Artwork("Sunset Overdrive", "A beautiful sunset painting", LocalDate.of(2023, 10, 14), "Oil on canvas", "http://example.com/sunset.jpg", 1);
        boolean result = galleryService.addArtwork(newArtwork);
        assertTrue("Artwork should be added successfully", result);
    }

    @Test
    public void testUpdateArtwork() throws ArtWorkNotFoundException {
        // Add a new artwork to update later
        Artwork newArtwork = new Artwork("Sunset Overdrive", "A beautiful sunset painting", LocalDate.of(2023, 10, 14), "Oil on canvas", "http://example.com/sunset.jpg", 1);
        galleryService.addArtwork(newArtwork);

        // Fetch the artwork to update it
        int artworkID = galleryService.getNextArtworkId() - 1; // Assuming this gives us the last added ID
        Artwork existingArtwork = galleryService.getArtworkById(artworkID);

        // Modify some details
        existingArtwork.setTitle("Sunset Overdrive Updated");
        existingArtwork.setDescription("An updated description of the sunset painting.");

        // Perform the update
        boolean result = galleryService.updateArtwork(existingArtwork);
        assertTrue("Artwork should be updated successfully", result);

        // Fetch again and verify
        Artwork updatedArtwork = galleryService.getArtworkById(artworkID);
        assertEquals("Sunset Overdrive Updated", updatedArtwork.getTitle());
        assertEquals("An updated description of the sunset painting.", updatedArtwork.getDescription());
    }

    @Test
    public void testRemoveArtwork() throws ArtWorkNotFoundException {
        // Add a new artwork to remove later
        Artwork newArtwork = new Artwork("Temp Artwork", "A temporary artwork to be deleted", LocalDate.of(2023, 10, 15), "Watercolor", "http://example.com/temp.jpg", 1);
        galleryService.addArtwork(newArtwork);

        // Fetch the ID of the added artwork
        int artworkID = galleryService.getNextArtworkId() - 1; // Assuming this gives us the last added ID

        // Perform the removal
        boolean result = galleryService.removeArtwork(artworkID);
        assertTrue("Artwork should be removed successfully", result);

        // Verify removal by expecting ArtWorkNotFoundException to be thrown
        try {
            galleryService.getArtworkById(artworkID);
            fail("Expected ArtWorkNotFoundException to be thrown");
        } catch (ArtWorkNotFoundException e) {
            assertEquals("Artwork with ID " + artworkID + " not found.", e.getMessage());
        }
    }

    @Test
    public void testSearchArtworks() {
        // Add some artworks for searching
        galleryService.addArtwork(new Artwork("Morning Light", "Sunrise over the hills", LocalDate.of(2023, 10, 16), "Acrylic", "http://example.com/morning.jpg", 2));
        galleryService.addArtwork(new Artwork("Evening Calm", "Sunset over the ocean", LocalDate.of(2023, 10, 16), "Oil", "http://example.com/evening.jpg", 2));
    
        // Test search where results are expected
        List<Artwork> results = galleryService.searchArtworks("Sunset");
        assertNotNull("Search results should not be null", results);
        assertFalse("Expected at least one result for 'Sunset'", results.isEmpty());
        assertTrue(results.stream().anyMatch(artwork -> artwork.getTitle().contains("Sunset") || artwork.getDescription().contains("Sunset")));
    
        // Test search where no results are expected
        List<Artwork> noResults = galleryService.searchArtworks("Unicorn");
        assertNotNull("Search results should not be null", noResults);
        assertTrue("Expected no results for 'Unicorn'", noResults.isEmpty());
    }
}