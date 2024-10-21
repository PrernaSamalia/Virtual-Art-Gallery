-- Created the database 'virtualart'
CREATE DATABASE IF NOT EXISTS `virtualart`;

-- Switched to using the 'virtualart' database
USE virtualart;


/********************************************** Table Structure Definitions **********************************************/

-- Created the 'Artwork' table
CREATE TABLE Artwork (
    ArtworkID INT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Description TEXT,
    CreationDate DATE NOT NULL,
    Medium VARCHAR(100) NOT NULL,
    ImageURL VARCHAR(255) NOT NULL UNIQUE,
    ArtistID INT NOT NULL,
    FOREIGN KEY (ArtistID) REFERENCES Artist(ArtistID) ON DELETE CASCADE
);

-- Created the 'Artist' table
CREATE TABLE Artist (
    ArtistID INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Biography TEXT,
    BirthDate DATE NOT NULL,
    Nationality VARCHAR(100) NOT NULL,
    Website VARCHAR(255) UNIQUE,
    ContactInformation VARCHAR(255) UNIQUE
);

-- Created the 'User' table
CREATE TABLE User (
    UserID INT PRIMARY KEY,
    Username VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    DateOfBirth DATE NOT NULL,
    ProfilePicture VARCHAR(500),
    FavouriteArtworks INT,
    FOREIGN KEY (FavouriteArtworks) REFERENCES Artwork(ArtworkID) ON DELETE SET NULL
);

-- Created the 'Gallery' table
CREATE TABLE Gallery (
    GalleryID INT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description TEXT,
    Location VARCHAR(255) NOT NULL,
    Curator INT,
    OpeningHours TIME,
    FOREIGN KEY (Curator) REFERENCES Artist(ArtistID) ON DELETE SET NULL
);

-- Junction Table for User's Favorite Artworks (Many-to-Many between User and Artwork)
CREATE TABLE User_Favorite_Artwork (
    UserID INT,
    ArtworkID INT,
    PRIMARY KEY (UserID, ArtworkID),
    FOREIGN KEY (UserID) REFERENCES User(UserID) ON DELETE CASCADE,
    FOREIGN KEY (ArtworkID) REFERENCES Artwork(ArtworkID) ON DELETE CASCADE
);

-- Junction Table for Artwork displayed in Galleries (Many-to-Many between Artwork and Gallery)
CREATE TABLE Artwork_Gallery (
    ArtworkID INT,
    GalleryID INT,
    PRIMARY KEY (ArtworkID, GalleryID),
    FOREIGN KEY (ArtworkID) REFERENCES Artwork(ArtworkID) ON DELETE CASCADE,
    FOREIGN KEY (GalleryID) REFERENCES Gallery(GalleryID) ON DELETE CASCADE
);

SELECT * FROM Artwork;
SELECT * FROM Artist;
SELECT * FROM User;
SELECT * FROM Gallery;

DROP DATABASE IF EXISTS `virtualart`;
DROP TABLE Artwork;
DROP TABLE Artist;
DROP TABLE User;
DROP TABLE Gallery;

DELETE FROM Gallery
WHERE GalleryID = 14;