package nl.jordy.petplacer.helpers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidEnumValidator implements ConstraintValidator<ValidEnumValue, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;
    String allowedValues;

    @Override
    public void initialize(ValidEnumValue annotation) {
        enumClass = annotation.enumClass();
        this.allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .filter(status -> !status.name().equals("INVALID"))
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // Check if the value is INVALID
        if (value.name().equals("INVALID")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid value. Must be any of: " + allowedValues)
                    .addConstraintViolation();
            return false;
        }

        try {
            Enum.valueOf(enumClass.asSubclass(Enum.class), value.name());
            return true;
        } catch (IllegalArgumentException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid value. Must be any of: " + allowedValues)
                    .addConstraintViolation();
            return false;
        }
    }
}

