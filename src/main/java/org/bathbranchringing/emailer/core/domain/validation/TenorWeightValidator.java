package org.bathbranchringing.emailer.core.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.bathbranchringing.emailer.core.domain.Tower;

public class TenorWeightValidator implements ConstraintValidator<ValidateTenorWeight, Tower> {

    @Override
    public void initialize(ValidateTenorWeight arg0) {
    }

    @Override
    public boolean isValid(Tower tower, ConstraintValidatorContext context) {
        return (tower.getTenorWeightCwt() > 0)
                || (tower.getTenorWeightQtrs() > 0)
                || (tower.getTenorWeightLbs() > 0);
    }

}
