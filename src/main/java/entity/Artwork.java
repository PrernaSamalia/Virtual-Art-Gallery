package entity;

import java.time.LocalDate;

/**
 * Represents an Artwork entity in the Virtual Art Gallery system.
 * This class contains details about an artwork, including its title,
 * description, creation date, medium, image URL, and associated artist.
*/
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

    // Parametrized constructor with artworkID
    public Artwork(int artworkID, String title, String description, LocalDate creationDate, String medium, String imageURL, int artistID) {

        this.artworkID = artworkID;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.medium = medium;
        this.imageURL = imageURL;
        this.artistID = artistID;
    }

    // Parametrized constructor without artworkID
    public Artwork(String title, String description, LocalDate creationDate, String medium, String imageURL, int artistID) {

        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.medium = medium;
        this.imageURL = imageURL;
        this.artistID = artistID;
    }

    /***************************** Getters and Setters *****************************/

    // Getter and Setter for artworkID
    public int getArtworkID() {
        return artworkID;
    }
    public void setArtworkID(int artworkID) {
        this.artworkID = artworkID;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for creationDate
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    // Getter and Setter for medium
    public String getMedium() {
        return medium;
    }
    public void setMedium(String medium) {
        this.medium = medium;
    }

    // Getter and Setter for imageURL
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // Getter and Setter for artistID
    public int getArtistID() {
        return artistID;
    }
    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }
}