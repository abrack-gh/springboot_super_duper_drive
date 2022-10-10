package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final UserService userService;


    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(
            Authentication authentication, @ModelAttribute("newFile")FileForm newFile,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            @ModelAttribute("newNote") NoteForm newNote, Model model){
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", this.credentialService.getCredentialListings(user.getUserid()));
        model.addAttribute("encryptionService", encryptionService);

        return "home";

    }

    @PostMapping("add-credential")
    public String newCredential(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newCredential") CredentialForm newCredential, @ModelAttribute("newNote") NoteForm newNote,
            Model model){
        String username = authentication.getName();
        String newurl = newCredential.getUrl();
        String credentialIdStr = newCredential.getCredentialid();
        String password = newCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        Integer encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if (credentialIdStr.isEmpty()) {
            credentialService.addCredential(newurl, username, newCredential.getUsername(), encodedKey, encryptedPassword);
        } else {
            Credential existingCredential = getCredential(Integer.parseInt(credentialIdStr));
            credentialService.updateCredential(existingCredential.getCredentialId(), newCredential.getUsername(), encodedKey, newurl, encryptedPassword);
        }
        User user = userService.getUser(username);
        model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
        }

        @GetMapping(value = "/get-credential{credentialid}")
        public Credential getCredential(@PathVariable Integer credentialid){
        return credentialService.getCredential(credentialid);
        }

        @GetMapping(value = "/delete-credential/{credentialid}")
        public String deleteCredential(
                Authentication authentication, @PathVariable Integer credentialid,
                @ModelAttribute("newCredential") CredentialForm newCredential, @ModelAttribute("newFile") FileForm newFile,
                @ModelAttribute("newNote") NoteForm newNote, Model model){
        credentialService.deleteCredential(credentialid);
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
        }

}



