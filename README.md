# Virtual Art Gallery

A simple Virtual Art Gallery system built in Java, designed to manage artworks, galleries, and user favorites. It provides functionality for adding, viewing, updating, and removing artworks, managing galleries, and maintaining a list of favorite artworks for users using a menu-driven interface.

## Features
- **Add New Artwork**: Add details of a new artwork to the gallery.
- **Update Artwork**: Modify details of existing artworks.
- **Remove Artwork**: Delete artworks from the gallery.
- **Get Artwork by ID**: Retrieve details of a specific artwork using its ID.
- **Search Artworks**: Search artworks based on keywords (title, description, medium).
- **Add Artwork to User Favorites**: Mark artworks as favorites for a user.
- **Remove Artwork from User Favorites**: Unmark artworks from a user's favorites.
- **Get User Favorite Artworks**: Retrieve the list of favorite artworks for a specific user.

## Prerequisites
- **Java 8+**: Make sure Java is installed and added to your system's PATH.
- **MySQL**: Set up a MySQL server to host the virtual art gallery database.
- **MySQL Connector and Junit**: Ensure `mysql-connector-java` and `junit 4.13.2` is added to your pom.xml dependency.

## Setup
1. **Clone the Repository**
   ```bash
   git clone https://github.com/kishlaykiku/Virtual-Art-Gallery.git
   cd 'Virtual-Art-Gallery'
   ```

2. **Set Up Database**

    ```bash
    Execute the SQL scripts in the /sql folder:
    CreateSchema.sql to create tables.
    InsertData.sql to insert sample data.
    ```

3. **Configure Database Connection**

    ```bash
    hostname = localhost
    port = 3306
    dbname = hospital_db
    username = your_username
    password = your_password
    ```

4. Compile and Run the Project

    ```bash
    javac src/main/MainModule.java
    java src/main/MainModule
    ```

4. If using Maven, you can run JUnit tests with

    ```bash
    mvn test
    ```
<hr>
<hr>