package com.seven.spring.controller;

import com.seven.spring.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    // @RequestMapping (value = "test", method = RequestMethod.GET)
    @GetMapping("test")
    public void test(@ModelAttribute("list") List<String> list, ModelMap modelMap) {
        System.out.println(modelMap.get("list"));
        list.stream().forEach(s -> System.out.println(s));
    }
    @GetMapping("test1")
    public void test1(@RequestParam("list") List<String> list) {
        list.stream().forEach(s -> System.out.println(s));
    }
    @GetMapping("test2")
    public void test2(@CookieValue("list") Cookie cookie, @CookieValue("test") String test) {
        System.out.println(cookie.getName());
        System.out.println(cookie.getValue());
        System.out.println(test);
    }
    @ModelAttribute("user")
    public User addAccount() {
        return new User("jz","123");
    }

    @RequestMapping (value = "user", method = RequestMethod.GET)
    public String helloWorld(User user, ModelMap model) {
        System.out.println(user.getName());

        user.setName("jizhou");
        User user1 = (User) model.get("user");
        System.out.println(user1.getName());
        return user.toString();
    }
    @GetMapping("download")
    public void download(@RequestParam(value = "date") LocalDate date){
        System.out.println(date);
    }
}
