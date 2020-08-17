package com.xjw.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;

@Data
public class UserVo {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    private Integer age;

    @NotBlank(message = "生日不能为空")
    private String birthday;

    @NotBlank(message = "性别不能为空")
    private String sex;

    @Valid
    private Employee employee;
}
