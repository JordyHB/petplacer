package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.exceptions.BadRequestException;
import org.springframework.validation.BindingResult;

public class CheckBindingResult {

    public static void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult);
        }
    }
}
