package nl.jordy.petplacer.helpers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidEnumValidator implements ConstraintValidator<ValidEnumValue, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;
    String allowedValues;
    String fieldName;

    @Override
    public void initialize(ValidEnumValue annotation) {
        enumClass = annotation.enumClass();
        // Gets all valid values from the enum class excluding INVALID
        this.allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .filter(status -> !status.name().equals("INVALID"))
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        this.fieldName = annotation.fieldName();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // Check if the value is INVALID
        if (value.name().equals("INVALID")) {

            // catches @Validated specifically and throws an exception
            if (context.getDefaultConstraintMessageTemplate().equals("Invalid value. Must be any of: {enumClass}")) {
                throw new BadRequestException(
                        "Invalid value for " + fieldName + ", must be any of: " + allowedValues
                );
            }

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value for " + fieldName + ", must be any of: " + allowedValues
                    )
                    .addConstraintViolation();
            return false;
        }

        // catches the cases from the DTOs and throws an exception
        try {
            Enum.valueOf(enumClass.asSubclass(Enum.class), value.name());
            return true;
        } catch (IllegalArgumentException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value for " + fieldName + ", must be any of: " + allowedValues
                    )
                    .addConstraintViolation();
            return false;
        }
    }
}

