package MyAnnotation;

import com.xjw.exception.ValidListException;
import com.xjw.web.ValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidListValidator implements ConstraintValidator<ValidList, List> {
    Class<?>[] grouping;

    @Override
    public void initialize(ValidList validList) {
       grouping = validList.grouping();
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        Map<Integer,Set<ConstraintViolation<Object>>> errors = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            Set<ConstraintViolation<Object>> validateSet = ValidatorUtils.validator.validate(object, grouping);
            errors.put(i,validateSet);
        }
        if (errors.size() > 0) {
            throw new ValidListException(errors);
        }
        return false;
    }
}
