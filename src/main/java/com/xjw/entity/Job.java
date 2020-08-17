package com.xjw.entity;

import MyAnnotation.MultipleOfThree;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Job {
    @MultipleOfThree
    private Integer id;
    /**
     * @size在非空的情况下才起作用
     */
    @Size(min = 1)
    @NotNull
    private String name;
    @Size(min = 1)
    @NotNull
    private List<String> labels;
}
