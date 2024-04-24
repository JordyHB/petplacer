package nl.jordy.petplacer.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


public class BadRequestException extends RuntimeException {

    // Constructor to handle simple messages
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

    // Constructor that handles the input of BindingResults with multiple errors
    public BadRequestException(BindingResult bindingResult){
        super(buildErrorMessage(bindingResult));
    }

    private static String buildErrorMessage(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        // loops through all the errors transforming them into a pretty string
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("\n");
        }

        return errors.toString();
    }

}
