package com.xjw.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String code;

    private String msg;

    private Object data;

    public ApiResponse(String code,String msg,Object data){
        this.code= code;
        this.msg = msg;
        this.data = data;
    }
}
