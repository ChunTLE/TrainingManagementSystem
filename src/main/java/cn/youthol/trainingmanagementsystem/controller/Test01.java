package cn.youthol.trainingmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test01 {
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
