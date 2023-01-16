package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/credentials")
@Controller
public class CredentialController {

    private Logger logger = LoggerFactory.getLogger(CredentialController.class);
    private CredentialService credentialService;
    private UserMapper userMapper;

    public CredentialController(CredentialService credentialService, UserMapper userMapper) {
        this.credentialService = credentialService;
        this.userMapper = userMapper;
    }

    @PostMapping("/submit")
    public String submitCredentials(
            @ModelAttribute("CredentialStore") CredentialStore credentialStore,
            Authentication authentication,
            Model model) {


        String username = (String) authentication.getPrincipal();


        Boolean isSuccess = this.credentialService.insertOrUpdateCredential(credentialStore, username);


        return "redirect:/result?isSuccess=" + isSuccess;

    }
        


    @GetMapping("/credential")
    public String deleteCredentials(@ModelAttribute("credentialStore") CredentialStore credentialStore,
        @RequestParam(required = false, name = "credentialId") Integer credentialId,
        Authentication authentication,
        Model model){

        Boolean isSuccess = this.credentialService.deleteCredentials(credentialId);

        return "redirect:/result?isSuccess=" + isSuccess;
    }
}



