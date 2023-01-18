package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialsService;
    private UserMapper userMapper;

    private UserService userService;

    public HomeController(NoteService noteService, FileService fileService, CredentialService credentialsService, UserMapper userMapper) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialsService = credentialsService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

   /* @GetMapping
    public String home(@ModelAttribute("note") NoteForm note, @ModelAttribute("credential") CredentialForm credentialForm, Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        if(user != null) {
            int userId = user.getuserId();
            model.addAttribute("notes", noteService.getUserNote(userId));
            model.addAttribute("files", fileService.getUploadedFiles());
            model.addAttribute("credentials", credentialsService.getAllCredentials(userId));
            return "home";
        }
        return "signup";
    }

    */

   @GetMapping
    public String home(Authentication authentication, Model model, File file, Notes notes, Credential credential) {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        if(user != null) {
            int userId = user.getuserId();
            model.addAttribute("notes", notes);
            List<Notes> notesList = List.of(noteService.getNoteListings(userId));
            model.addAttribute("file", file);
            List<File> filesList = fileService.getUploadedFiles();
            model.addAttribute("credential", credential);
            List<Credential> credentials = credentialsService.getAllCredentials(userId);
            return "home";
        }
        return "signup";
    }

    @GetMapping("/result")
    public String showResult(
            Authentication authentication,
            @RequestParam(required = false, name = "isSuccess") boolean isSuccess,
            @RequestParam(required = false, name = "errorType") Integer errorType,
            Model model
    ) {

        Map<String, Object> data = new HashMap<>();

        data.put("isSuccess", isSuccess);
        data.put("errorType", errorType);

        model.addAllAttributes(data);

        return "result";
    }


}