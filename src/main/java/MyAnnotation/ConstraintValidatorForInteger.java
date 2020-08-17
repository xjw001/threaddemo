package MyAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConstraintValidatorForInteger implements ConstraintValidator<MultipleOfThree,Integer> {

    @Override
    public void initialize(MultipleOfThree constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value % 3 == 0;
    }
}
