package entity;

import java.time.LocalTime;

public class Gallery {
    private int galleryID;
    private String name;
    private String description;
    private String location;
    private int curator; // Foreign key
    private LocalTime openingHours;

    // Default constructor
    public Gallery() {}

    // Parametrized constructor
    public Gallery(int galleryID, String name, String description, String location, int curator, LocalTime openingHours) {
        this.galleryID = galleryID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.curator = curator;
        this.openingHours = openingHours;
    }

    public Gallery(String name, String description, String location, int curator, LocalTime openingHours) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.curator = curator;
        this.openingHours = openingHours;
    }

    // Getters and Setters
    public int getGalleryID() {
        return galleryID;
    }

    public void setGalleryID(int galleryID) {
        this.galleryID = galleryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCurator() {
        return curator;
    }

    public void setCurator(int curator) {
        this.curator = curator;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }
}