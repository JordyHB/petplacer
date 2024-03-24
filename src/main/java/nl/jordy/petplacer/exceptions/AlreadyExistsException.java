package nl.jordy.petplacer.exceptions;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public AlreadyExistsException() {
        super("Record already exists");
    }
}
