package com.xjw.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Null;

@Data
public class Employee {
    @Null(message = "id必须为空")
    private Integer id;
    @NotBlank
    private String deptId;
    @NotBlank
    private String addr;
}
