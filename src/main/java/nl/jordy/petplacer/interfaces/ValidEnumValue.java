package nl.jordy.petplacer.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nl.jordy.petplacer.helpers.ValidEnumValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEnumValidator.class)
public @interface ValidEnumValue {

    String message() default "Invalid value. Must be any of: {enumClass}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<? extends Enum<?>> enumClass();

}
