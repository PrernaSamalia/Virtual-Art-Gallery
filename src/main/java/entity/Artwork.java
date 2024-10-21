package entity;

import java.time.LocalDate;

public class Artwork {
    private int artworkID;
    private String title;
    private String description;
    private LocalDate creationDate;
    private String medium;
    private String imageURL;
    private int artistID; // Foreign key

    // Default constructor
    public Artwork() {}

    // Parametrized constructor
    public Artwork(int artworkID, String title, String description, LocalDate creationDate, String medium, String imageURL, int artistID) {
        this.artworkID = artworkID;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.medium = medium;
        this.imageURL = imageURL;
        this.artistID = artistID;
    }

    public Artwork(String title, String description, LocalDate creationDate, String medium, String imageURL, int artistID) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.medium = medium;
        this.imageURL = imageURL;
        this.artistID = artistID;
    }

    // Getters and Setters
    public int getArtworkID() {
        return artworkID;
    }

    public void setArtworkID(int artworkID) {
        this.artworkID = artworkID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }
}