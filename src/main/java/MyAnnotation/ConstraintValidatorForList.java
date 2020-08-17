package MyAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ConstraintValidatorForList implements ConstraintValidator<MultipleOfThree, List> {

    @Override
    public void initialize(MultipleOfThree constraintAnnotation) {

    }

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.size()%3 == 0;
    }


}
