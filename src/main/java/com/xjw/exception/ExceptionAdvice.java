package com.xjw.exception;

import com.xjw.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    @ExceptionHandler
    public ApiResponse ExceptionHandler(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        Map<String, String> resResult = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return new ApiResponse("1001","异常",resResult);
    }

    public ApiResponse validExceptionHandler(ConstraintViolationException e){
        Map<Path, String> collect = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        return new ApiResponse("1001","异常",collect);
    }

    @ExceptionHandler
    public ApiResponse validListException(ValidListException e){
        Map<Integer,Map<Path,String>> result = new HashMap();
        e.getErrors().forEach((integer,constraintViolations)->{
//            Map<Path,String> m = new HashMap();
//            for (ConstraintViolation constraintViolation:constraintViolations) {
//                m.put(constraintViolation.getPropertyPath(),constraintViolation.getMessage());
//            }
//            result.put(integer,m);
            result.put(integer,constraintViolations.stream()
                    .collect(Collectors.toMap(ConstraintViolation::getPropertyPath,ConstraintViolation::getMessage)));
        });
        return new ApiResponse("1001","异常",result);
    }
}
