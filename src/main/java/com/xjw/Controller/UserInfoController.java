package com.xjw.Controller;

import MyAnnotation.ValidList;
import com.xjw.entity.Employee;
import com.xjw.entity.Job;
import com.xjw.entity.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserInfoController {

    @PostMapping(value = "/test")
    public String test(@RequestBody @Valid UserVo userVo) {
        log.info("姓名：" + userVo.getUserName());
        log.info("年龄:"+userVo.getAge());
        return "sadfsaddfa";
    }

    @PostMapping(value = "/job")
    public String addJob(@RequestBody @Valid Job job){
        return "success";
    }

    @PostMapping(value = "/listEmp")
    public String addJob(@RequestBody @ValidList(grouping = {Employee.class, Default.class}) List<Employee> listEmp){

        return "success";
    }
}
