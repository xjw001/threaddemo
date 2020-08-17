package com.xjw.exception;

import javax.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;

public class ValidListException extends RuntimeException {

    private Map<Integer, Set<ConstraintViolation<Object>>> errors;

    public ValidListException(Map<Integer, Set<ConstraintViolation<Object>>> errors) {
        this.errors = errors;
    }

    public Map<Integer, Set<ConstraintViolation<Object>>> getErrors() {
        return errors;
    }

    public void setErrors(Map<Integer, Set<ConstraintViolation<Object>>> errors) {
        this.errors = errors;
    }
}
