package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
//@ResponseBody
public class StaticWebPageController {

    /*@GetMapping("/")
    public String index(){
        return "index";
    }*/

    private static final int MY_VARIABLE = 2;

    //@GetMapping("/")
    @RequestMapping("/")
    public String index(Map<String,Object> model){
        model.put("variable", "My Thymeleaf variable");
        model.put("one", 1);
        model.put("two", 2);
        model.put("myVariable", MY_VARIABLE);
        return "index";
    }
}
