package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
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
            int userid = user.getuserid();
            model.addAttribute("notes", noteService.getUserNote(userid));
            model.addAttribute("files", fileService.getUploadedFiles());
            model.addAttribute("credentials", credentialsService.getAllCredentials(userid));
            return "home";
        }
        return "signup";
    }

    */

    @GetMapping("/home")
    public String home(Authentication authentication, Model model, File file, @ModelAttribute("noteObject") Notes notes, @ModelAttribute("credentialObject") Credential credential) {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        if (user != null) {
            int userid = user.getuserid();
            model.addAttribute("notes", notes);
            List<Notes> notesList = noteService.getNoteListings(userid);
            model.addAttribute("file", file);
            List<File> filesList = fileService.getUploadedFiles(userid);
            model.addAttribute("credential", credential);
            List<Credential> credentials = credentialsService.getAllCredentials(userid);
            return "home";
        }
        return "signup";
    }

    @PostMapping("/home/note")
    public String addNote(@ModelAttribute("noteObject") Notes notes, @ModelAttribute("credentialObject") Credential credential, Model model, Authentication authentication) {
        String username = authentication.getName();
        Integer userId = userMapper.getUser(username).getuserid();
        User user = userMapper.getUser(username);
        notes.setUserId(userId);

        String error = null;

        if (notes.getnoteId() == null) {
            try {
                noteService.addNote(notes);
            } catch (Exception e) {
                error = "Error";
            }
        } else {
            try {
                noteService.updateNote(notes);
            } catch (Exception e) {
                error = "Error";
            }
        }
        if (error == null) {
            model.addAttribute("isSuccess", true);
            model.addAttribute("errorType", false);
        } else {
            model.addAttribute("errorType", error);
        }

        return "result";
    }

    @DeleteMapping("/home/deleteNote/{id}")
    public String deleteFile(@PathVariable("id") Integer noteId, @ModelAttribute("noteObject") Notes notes, Model model, Authentication authentication) {

        String error = null;

        try {
            noteService.deleteNote(noteId);
        } catch (Exception e) {
            error = "Error";
        }
        if (error == null) {
            model.addAttribute("isSuccess", true);
            model.addAttribute("errorType", false);
        } else {
            model.addAttribute("errorType", error);
        }
        return "result";
    }

    @PostMapping("/home/credential")
    public String addCredential(@ModelAttribute("noteObject") Notes notes, @ModelAttribute("credentialObject") Credential credential, Model model, Authentication authentication) {

        String error = null;

        try {
            new URL(credential.getUrl()).toURI();
        } catch (Exception e) {
            error = "Error";
        }

        if (error == null) {
            String username = authentication.getName();
            if (credential.getCredentialId() == null) {
                credentialsService.addCredential(credential, username);
            } else {
                credentialsService.updateCredential(credential, username);
            }
        }

        return "result";

    }

    @DeleteMapping("/home/deleteCredential/{id}")
    public String deleteCredential(@PathVariable("id") Integer id, @ModelAttribute("credentialObject") Credential credential, @ModelAttribute("noteObject") Notes notes, Model model, Authentication authentication) {

        String error = null;

        try {
            credentialsService.deleteCredentials(id);
        } catch (Exception e) {
            error = "Error";
        }
        if (error == null) {
            model.addAttribute("isSuccess", true);
            model.addAttribute("errorType", false);
        } else {
            model.addAttribute("errorType", error);
        }
        return "result";
    }

    @PostMapping("/home/file-upload")
    public String fileUpload(@RequestParam("fileUpload")MultipartFile fileUpload, Model model, Authentication authentication) throws IOException {

        String username = authentication.getName();

        String error = null;

        if (fileUpload.isEmpty()) {
            error = "Empty file";
        }

        if (error == null) {
            String fileName = fileUpload.getOriginalFilename();
            boolean fileNameTaken = fileService.isfileNameAvailableForUser(username, fileName);
            if (fileNameTaken) {
                error = "File already exists";
            } else if (error == null) {
                fileService.saveFile(fileUpload, username);
                model.addAttribute("isSuccess", true);
                model.addAttribute("errorType", false);
            }} else {
                model.addAttribute("errorType", error);
            }
            return "result";
        }


    @GetMapping("/home/view-file/{id}")
    public String viewFile(@PathVariable("id") Integer id, HttpServletResponse httpServletResponse) throws IOException {
        File file = fileService.getFileById(id);
        httpServletResponse.setContentType(file.getcontentType());
        httpServletResponse.setContentLength(Integer.valueOf(file.getfileSize()));
        httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + file.getfileName() +"\"");

        FileCopyUtils.copy(file.getfileData(), httpServletResponse.getOutputStream());

        return "result";
    }



    @DeleteMapping("/home/delete-file/{id}")
    public String deleteFile(@PathVariable("id") Integer id, @ModelAttribute("credentialObject") Credential credential, @ModelAttribute("noteObject") Notes notes, Model model, Authentication authentication) throws IOException{

        String error = null;

        try{
            fileService.deleteFile(id);
        } catch (Exception e) {
            error = "Error";
        }

        if(error == null){
            model.addAttribute("isSuccess", true);
            model.addAttribute("errorType", false);
        } else {
            model.addAttribute("errorType", error);
        }

        return "result";
    }
}

