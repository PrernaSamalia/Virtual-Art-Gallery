-- Switched to using the 'virtualart' database
USE virtualart;

-- Insert data into Artist table
INSERT INTO Artist (ArtistID, Name, Biography, BirthDate, Nationality, Website, ContactInformation)
VALUES
(1, 'Pablo Picasso', 'Spanish painter and sculptor.', '1881-10-25', 'Spanish', 'http://picasso.com', 'contact@picasso.com'),
(2, 'Vincent van Gogh', 'Dutch Post-Impressionist painter.', '1853-03-30', 'Dutch', 'http://vangogh.com', 'contact@vangogh.com'),
(3, 'Leonardo da Vinci', 'Italian polymath of the Renaissance.', '1452-04-15', 'Italian', 'http://davinci.com', 'contact@davinci.com'),
(4, 'Claude Monet', 'Founder of French Impressionist painting.', '1840-11-14', 'French', 'http://monet.com', 'contact@monet.com'),
(5, 'Salvador Dali', 'Spanish surrealist artist.', '1904-05-11', 'Spanish', 'http://dali.com', 'contact@dali.com'),
(6, 'Henri Matisse', 'French artist known for his use of color.', '1869-12-31', 'French', 'http://matisse.com', 'contact@matisse.com'),
(7, 'Frida Kahlo', 'Mexican painter known for her portraits.', '1907-07-06', 'Mexican', 'http://kahlo.com', 'contact@kahlo.com'),
(8, 'Georgia O’Keeffe', 'American modernist artist.', '1887-11-15', 'American', 'http://okeeffe.com', 'contact@okeeffe.com'),
(9, 'Andy Warhol', 'American artist and film director.', '1928-08-06', 'American', 'http://warhol.com', 'contact@warhol.com'),
(10, 'Michelangelo', 'Italian sculptor, painter, architect, and poet.', '1475-03-06', 'Italian', 'http://michelangelo.com', 'contact@michelangelo.com');

-- Insert data into Artwork table
INSERT INTO Artwork (ArtworkID, Title, Description, CreationDate, Medium, ImageURL, ArtistID)
VALUES
(1, 'The Starry Night', 'A painting by Vincent van Gogh.', '1889-06-01', 'Oil on canvas', 'http://example.com/starrynight.jpg', 2),
(2, 'Mona Lisa', 'A portrait by Leonardo da Vinci.', '1503-10-01', 'Oil on poplar panel', 'http://example.com/monalisa.jpg', 3),
(3, 'The Persistence of Memory', 'A surrealist painting by Salvador Dali.', '1931-04-01', 'Oil on canvas', 'http://example.com/persistenceofmemory.jpg', 5),
(4, 'Girl with a Pearl Earring', 'A painting by Johannes Vermeer.', '1665-01-01', 'Oil on canvas', 'http://example.com/girlwithpearlearring.jpg', 3),
(5, 'The Scream', 'An iconic work by Edvard Munch.', '1893-01-01', 'Oil, tempera, pastel on cardboard', 'http://example.com/thescream.jpg', 1),
(6, 'Guernica', 'A mural-sized painting by Pablo Picasso.', '1937-06-01', 'Oil on canvas', 'http://example.com/guernica.jpg', 1),
(7, 'Water Lilies', 'A series of paintings by Claude Monet.', '1916-01-01', 'Oil on canvas', 'http://example.com/waterlilies.jpg', 4),
(8, 'The Last Supper', 'A mural by Leonardo da Vinci.', '1498-01-01', 'Tempera on gesso', 'http://example.com/lastsupper.jpg', 3),
(9, 'Campbells Soup Cans', 'A work by Andy Warhol.', '1962-01-01', 'Synthetic polymer paint on canvas', 'http://example.com/campbellsoup.jpg', 9),
(10, 'American Gothic', 'A painting by Grant Wood.', '1930-01-01', 'Oil on beaverboard', 'http://example.com/americangothic.jpg', 8);

-- Insert data into User table
INSERT INTO User (UserID, Username, Password, Email, FirstName, LastName, DateOfBirth, ProfilePicture, FavouriteArtworks)
VALUES
(1, 'artlover1', 'password1', 'artlover1@example.com', 'John', 'Doe', '1990-05-12', 'http://example.com/profile1.jpg', 1),
(2, 'vanfan', 'password2', 'vanfan@example.com', 'Jane', 'Smith', '1985-03-10', 'http://example.com/profile2.jpg', 2),
(3, 'dali_dreamer', 'password3', 'dali_dreamer@example.com', 'Alice', 'Johnson', '1992-07-19', 'http://example.com/profile3.jpg', 3),
(4, 'renaissance_man', 'password4', 'ren_man@example.com', 'Bob', 'Brown', '1988-02-22', 'http://example.com/profile4.jpg', 4),
(5, 'modernart_fan', 'password5', 'modernart@example.com', 'Charlie', 'Davis', '1995-08-15', 'http://example.com/profile5.jpg', 5),
(6, 'warhol_fanatic', 'password6', 'warhol_fan@example.com', 'Emily', 'White', '1982-12-05', 'http://example.com/profile6.jpg', 9),
(7, 'georgia_okeefe', 'password7', 'okeeffe@example.com', 'Michael', 'Miller', '1991-04-09', 'http://example.com/profile7.jpg', 8),
(8, 'painter4life', 'password8', 'painter4life@example.com', 'Sara', 'Moore', '1987-11-03', 'http://example.com/profile8.jpg', 7),
(9, 'surrealist_lover', 'password9', 'surrealist@example.com', 'Lucas', 'Taylor', '1993-06-21', 'http://example.com/profile9.jpg', 6),
(10, 'classic_art', 'password10', 'classicart@example.com', 'Ella', 'Anderson', '1989-09-30', 'http://example.com/profile10.jpg', 10);

-- Insert data into Gallery table
INSERT INTO Gallery (GalleryID, Name, Description, Location, Curator, OpeningHours)
VALUES
(1, 'Louvre', 'The worlds largest art museum.', 'Paris, France', 3, '09:00:00'),
(2, 'Van Gogh Museum', 'Houses the largest collection of Van Goghs works.', 'Amsterdam, Netherlands', 2, '10:00:00'),
(3, 'Museum of Modern Art', 'An iconic museum of modern art.', 'New York, USA', 9, '11:00:00'),
(4, 'Dali Theatre-Museum', 'A museum dedicated to Salvador Dali.', 'Figueres, Spain', 5, '10:00:00'),
(5, 'Rijksmuseum', 'The museum of the Netherlands.', 'Amsterdam, Netherlands', 3, '09:30:00'),
(6, 'Orangerie Museum', 'Famous for Monets Water Lilies.', 'Paris, France', 4, '11:30:00'),
(7, 'Uffizi Gallery', 'A prominent art museum in Italy.', 'Florence, Italy', 10, '08:30:00'),
(8, 'National Gallery', 'Art museum in Trafalgar Square.', 'London, UK', 6, '10:00:00'),
(9, 'Prado Museum', 'The main Spanish national art museum.', 'Madrid, Spain', 5, '09:45:00'),
(10, 'Tate Modern', 'Modern and contemporary art gallery.', 'London, UK', 9, '11:00:00');

-- Insert data into User_Favorite_Artwork table
INSERT INTO User_Favorite_Artwork (UserID, ArtworkID)
VALUES
(1, 1), (1, 2), (2, 3), (3, 5), (4, 2),
(5, 6), (6, 9), (7, 10), (8, 7), (9, 3);

-- Insert data into Artwork_Gallery table
INSERT INTO Artwork_Gallery (ArtworkID, GalleryID)
VALUES
(1, 2), (2, 1), (3, 4), (4, 5), (5, 6),
(6, 9), (7, 6), (8, 7), (9, 3), (10, 10);