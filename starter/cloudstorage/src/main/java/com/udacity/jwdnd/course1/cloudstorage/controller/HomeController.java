package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final EncryptionService encryptionService;
    private final HashService hashService;
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;

    private final CredentialService credentialService;

    public HomeController(EncryptionService encryptionService, HashService hashService,
                          FileService fileService, UserService userService,
                          NoteService noteService, CredentialService credentialService) {
        this.encryptionService = encryptionService;
        this.hashService = hashService;
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;

    }

    public EncryptionService getEncryptionService() {
        return encryptionService;
    }

    public HashService getHashService() {
        return hashService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public UserService getUserService() {
        return userService;
    }

    public NoteService getNoteService() {
        return noteService;
    }



    @GetMapping
    public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
                              @ModelAttribute("newNote") NoteForm noteForm, @ModelAttribute("newCredential") CredentialForm credentialForm,
                              Model model) {
        Integer userId = getUserId(authentication);
        model.addAttribute("files", this.fileService.getFileListings(userId));
        model.addAttribute("notes", noteService.getNoteListings(userId));
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);


        return "home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserid();
    }
}