package entity;

import java.time.LocalTime;

/**
 * Represents a Gallery entity in the Virtual Art Gallery system.
 * This class contains details about a gallery, including its name,
 * description, location, curator, and opening hours.
*/
public class Gallery {

    private int galleryID;
    private String name;
    private String description;
    private String location;
    private int curator; // Foreign key
    private LocalTime openingHours;

    // Default constructor
    public Gallery() {}

    // Parametrized constructor with galleryID
    public Gallery(int galleryID, String name, String description, String location, int curator, LocalTime openingHours) {

        this.galleryID = galleryID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.curator = curator;
        this.openingHours = openingHours;
    }

    // Parametrized constructor without galleryID
    public Gallery(String name, String description, String location, int curator, LocalTime openingHours) {

        this.name = name;
        this.description = description;
        this.location = location;
        this.curator = curator;
        this.openingHours = openingHours;
    }

    /***************************** Getters and Setters *****************************/

    // Getter and Setter for galleryID
    public int getGalleryID() {
        return galleryID;
    }
    public void setGalleryID(int galleryID) {
        this.galleryID = galleryID;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // Getter and Setter for curator
    public int getCurator() {
        return curator;
    }
    public void setCurator(int curator) {
        this.curator = curator;
    }

    // Getter and Setter for openingHours
    public LocalTime getOpeningHours() {
        return openingHours;
    }
    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }
}