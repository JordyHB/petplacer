package nl.jordy.petplacer.exceptions;

public class BadLoginException extends RuntimeException {

    public BadLoginException(String errorMessage) {
        super(errorMessage);
    }

    public BadLoginException() {
        super("Invalid username or password");
    }
}
