package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@RequestMapping("/credentials")
@Controller
public class CredentialController {

    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

        private final CredentialService credentialService;
        private final EncryptionService encryptionService;
        private final UserService userService;

        public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
            this.credentialService = credentialService;
            this.encryptionService = encryptionService;
            this.userService = userService;
        }

    @PostMapping("/submit-credential")
    public String submitCredentials(
            @ModelAttribute("newCredential") CredentialForm newCredential,
            Authentication authentication,
            Model model) {

        String username = (String) authentication.getName();
        String newUrl = newCredential.getUrl();
        String credentialIdStr = newCredential.getcredentialId();
        String password = newCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);


        if(credentialIdStr.isEmpty()){
            credentialService.addCredential(newUrl, username, newCredential.getUsername(), encodedKey, encryptedPassword);
        } else {
            Credential credentialExists = getCredential(Integer.parseInt(credentialIdStr));
            credentialService.update(credentialExists.getCredentialId(), newCredential.getUsername(), newUrl, encodedKey, encryptedPassword);
        }


        return "redirect:/result";

    }

    @GetMapping("/view-credential/{credentialId}")
    public Credential getCredential(@PathVariable Integer credentialId){
        return credentialService.getCredential(credentialId);
    }
        


    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredentials(@ModelAttribute("credentialStore") CredentialStore credentialStore,
        @RequestParam(required = false, name = "credentialId") Integer credentialId,
        Authentication authentication,
        Model model){

        Boolean isSuccess = credentialService.deleteCredentials(credentialId);

        return "redirect:/result?isSuccess=" + isSuccess;
    }
}



