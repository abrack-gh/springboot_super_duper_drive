package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ResultController {
    @GetMapping("/result")
    public String getResultPage(Model model){
        return "result";
    }
}
