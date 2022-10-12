package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class LoginController {
    @ModelAttribute("user")
    public User getUserModel(){
        return new User();
    }
    @GetMapping("/login")
    public String getLoginView(Model model) {
        model.addAttribute("welcomeMessage", "Welcome to the Login Page");
        return "login";
    }
}
