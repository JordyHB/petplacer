package nl.jordy.petplacer.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public RecordNotFoundException() {
        super("Record not found");
    }
}
