package dao;

import entity.Gallery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DBConnUtil;
import java.time.LocalTime;
import java.util.List;
import static org.junit.Assert.*;

/**
 * JUnit test class for testing the Gallery management functionalities
 * in the VirtualArtGalleryImpl class.
*/
public class VirtualArtGalleryImplGalleryTest {

    private VirtualArtGalleryImpl galleryService;

    /**
     * Setup method to initialize the VirtualArtGalleryImpl instance
     * before each test is executed.
    */
    @Before
    public void setUp() {
        galleryService = new VirtualArtGalleryImpl();
    }

    /**
     * Tear down method to close the database connection
     * after all tests are completed.
    */
    @After
    public void tearDown() {
        
        DBConnUtil.dbClose();
    }

    /**
     * Test case to verify adding a new gallery to the system.
     * Ensures that the gallery is added successfully.
    */
    @Test
    public void testAddGallery() {

        Gallery newGallery = new Gallery("Modern Art Gallery", "A gallery for modern art lovers", "New York", 1, LocalTime.of(9, 0));
        boolean result = galleryService.addGallery(newGallery);
        assertTrue("Gallery should be added successfully", result);
    }

    /**
     * Test case to verify updating an existing gallery's details.
     * Ensures that changes are correctly saved.
    */
    @Test
    public void testUpdateGallery() {

        // Add a new gallery to update later
        Gallery newGallery = new Gallery("Contemporary Art Gallery", "For contemporary art enthusiasts", "San Francisco", 2, LocalTime.of(10, 0));
        galleryService.addGallery(newGallery);

        // Fetch the gallery to update it
        int galleryID = galleryService.getNextGalleryId() - 1; // Assuming this gives us the last added ID
        Gallery existingGallery = galleryService.getGalleryById(galleryID);

        // Modify some details
        existingGallery.setName("Updated Contemporary Art Gallery");
        existingGallery.setDescription("Updated description for contemporary art.");

        // Perform the update
        boolean result = galleryService.updateGallery(existingGallery);
        assertTrue("Gallery should be updated successfully", result);

        // Fetch again and verify
        Gallery updatedGallery = galleryService.getGalleryById(galleryID);
        assertEquals("Updated Contemporary Art Gallery", updatedGallery.getName());
        assertEquals("Updated description for contemporary art.", updatedGallery.getDescription());
    }

    /**
     * Test case to verify removing a gallery from the system.
     * Ensures that the gallery is successfully deleted.
    */
    @Test
    public void testRemoveGallery() {

        // Add a new gallery to remove later
        Gallery newGallery = new Gallery("Temporary Gallery", "A gallery to be deleted", "Los Angeles", 3, LocalTime.of(11, 0));
        galleryService.addGallery(newGallery);

        // Fetch the ID of the added gallery
        int galleryID = galleryService.getNextGalleryId() - 1; // Assuming this gives us the last added ID

        // Perform the removal
        boolean result = galleryService.removeGallery(galleryID);
        assertTrue("Gallery should be removed successfully", result);

        // Verify removal by trying to fetch it again
        Gallery deletedGallery = galleryService.getGalleryById(galleryID);
        assertNull("Deleted gallery should not be found", deletedGallery);
    }

    /**
     * Test case to verify searching for galleries using keywords.
     * Tests both cases where results are expected and where no results are expected.
    */
    @Test
    public void testSearchGalleries() {

        // Add a few galleries for searching
        galleryService.addGallery(new Gallery("Sunrise Gallery", "Gallery focusing on morning landscapes", "Chicago", 4, LocalTime.of(8, 30)));
        galleryService.addGallery(new Gallery("Nightfall Gallery", "Gallery focusing on night scenes", "Chicago", 4, LocalTime.of(18, 0)));

        // Test search where results are expected
        List<Gallery> results = galleryService.searchGalleries("Gallery");
        assertNotNull("Search results should not be null", results);
        assertFalse("Expected at least one result for 'Gallery'", results.isEmpty());

        // Verify that one of the galleries contains "Gallery"
        boolean found = results.stream().anyMatch(gallery -> gallery.getName().contains("Gallery") || gallery.getDescription().contains("Gallery"));
        assertTrue("Expected to find at least one gallery with 'Gallery'", found);

        // Test search where no results are expected
        List<Gallery> noResults = galleryService.searchGalleries("Unicorn");
        assertNotNull("Search results should not be null", noResults);
        assertTrue("Expected no results for 'Unicorn'", noResults.isEmpty());
    }
}