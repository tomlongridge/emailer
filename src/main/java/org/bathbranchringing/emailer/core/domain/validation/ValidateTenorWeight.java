package org.bathbranchringing.emailer.core.domain.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { TenorWeightValidator.class })
@Documented
public @interface ValidateTenorWeight {

    String message() default "Tenor weight must be at least 1lb";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
}
