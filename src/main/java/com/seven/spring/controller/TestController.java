package com.seven.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class TestController {
    @GetMapping("test")
    public void test(@ModelAttribute List<String> list) {
        list.stream().forEach(s -> System.out.println(s));
    }
    @GetMapping("test1")
    public void test1(@RequestParam("list") List<String> list) {
        list.stream().forEach(s -> System.out.println(s));
    }
    @GetMapping("test2")
    public void test2(String[] list) {
        Arrays.stream(list).forEach(s -> System.out.println(s));
    }
}
