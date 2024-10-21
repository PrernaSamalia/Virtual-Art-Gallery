package exception.myexceptions;

/**
 * Custom exception to indicate that an artwork with the specified ID was not found.
 */
public class ArtWorkNotFoundException extends Exception {
    public ArtWorkNotFoundException(String message) {
        super(message);
    }
}