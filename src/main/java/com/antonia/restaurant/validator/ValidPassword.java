package com.antonia.restaurant.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom annotation for password complexity:
 *  Must contain uppercase, lowercase and special character, length >= 6
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Password must contain uppercase, lowercase and special character.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
