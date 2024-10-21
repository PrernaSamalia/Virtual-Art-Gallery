package entity;

import java.time.LocalDate;

/**
 * Represents an Artist entity in the Virtual Art Gallery system.
 * This class contains information about an artist, including their name,
 * biography, birth date, nationality, website, and contact information.
*/
public class Artist {

    private int artistID;
    private String name;
    private String biography;
    private LocalDate birthDate;
    private String nationality;
    private String website;
    private String contactInformation;

    // Default constructor
    public Artist() {}

    // Parametrized constructor
    public Artist(int artistID, String name, String biography, LocalDate birthDate, String nationality, String website, String contactInformation) {

        this.artistID = artistID;
        this.name = name;
        this.biography = biography;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.website = website;
        this.contactInformation = contactInformation;
    }

    /***************************** Getters and Setters *****************************/

    // Getter and Setter for artistID
    public int getArtistID() {
        return artistID;
    }
    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for biography
    public String getBiography() {
        return biography;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }

    // Getter and Setter for birthDate
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    // Getter and Setter for nationality
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    // Getter and Setter for website
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    // Getter and Setter for contactInformation
    public String getContactInformation() {
        return contactInformation;
    }
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}