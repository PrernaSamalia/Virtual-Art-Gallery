package exception.myexceptions;

/**
 * Custom exception to indicate that a user with the specified ID was not found.
*/
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {

        super(message);
    }
}