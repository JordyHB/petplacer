package nl.jordy.petplacer.exceptions;

public class CustomAccessDeniedException extends RuntimeException {

    public CustomAccessDeniedException(String message) {
        super(message);
    }

    public CustomAccessDeniedException() {
        super("You do not have permission to perform this action, please contact the administrator.");
    }
}
