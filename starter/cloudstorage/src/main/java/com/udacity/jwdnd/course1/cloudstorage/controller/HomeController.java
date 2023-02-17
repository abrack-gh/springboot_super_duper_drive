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

    public Integer userid(String username){

        return userService.getUser(username).getuserid();
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

    @RequestMapping("/home")
    public String home(Authentication authentication, Model model, File file, @ModelAttribute("notes") Notes notes, @ModelAttribute("credential") Credential credential) {
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
    public String addNote(@ModelAttribute("notes") Notes notes, @ModelAttribute("credential") Credential credential, Model model, Authentication authentication) {
        String username = authentication.getName();
        Integer userid = userMapper.getUser(username).getuserid();
        User user = userMapper.getUser(username);
        notes.setUserId(userid);

        String error = null;

        if (notes.getNoteid() == null) {
            try {
                noteService.addNote(notes, username);
            } catch (Exception e) {
                error = "Error";
            }
        } else {
            try {
                noteService.updateNote(notes, username);
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

    @PostMapping("/home/deleteNote/{id}")
    public String deleteFile(@PathVariable("id") Integer noteId, @ModelAttribute("notes") Notes notes, Model model, Authentication authentication) {

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
    public String addCredential(@ModelAttribute("credential") Credential credential, @ModelAttribute("note")Notes notes ,
                                Model model, Authentication authentication, String username,
                                User user) {

        Map<Integer, String> passwordMap = new HashMap<>();

        Integer userid = user.getuserid();


        if(credential.getCredentialId() == null){
            int credentialId = credentialsService.insertCredential(credential, username);
        } else {
            credentialsService.updateCredential(credential, username);
        }

        model.addAttribute("userNotes", this.noteService.getNoteListings(userid));
        model.addAttribute("unencryptedPasswordMap", credentialsService.getUnencryptedPassword(userid));
        model.addAttribute("userCredentials", this.credentialsService.getAllCredentials(userid));
        model.addAttribute("files", this.fileService.getAllFiles(userid));

        return "result";

    }

    @PostMapping("/home/deleteCredential/{id}")
    public String deleteCredential(@PathVariable("id") Integer id, @ModelAttribute("credential") Credential credential, @ModelAttribute("notes") Notes notes, Model model,
                                   Authentication authentication, String username) {

        Integer userid = userService.getUser(username).getuserid();

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

        model.addAttribute("files", this.fileService.getAllFiles(userid));

        return "result";
    }
    @PostMapping("/home/file-upload")
    public String handleFileUpload(@RequestParam("file")MultipartFile fileUpload, File file, Model model, Authentication authentication) throws IOException {

        String username = authentication.getName();

        String error = null;

        if(fileUpload.isEmpty() ){
            error = "Failed to store empty file.";
        }

        if(error == null){
            String fileName = fileUpload.getOriginalFilename();
            boolean filenameTaken = fileService.isfilenameAvailableForUser(username, fileName);
            if(filenameTaken){
                error = "You have already stored a file under similar name";
            }
        }

        if(error == null){
            fileService.saveFile(file, username);
            model.addAttribute("successMessage", true);
            model.addAttribute("failureMessage", false);
        }else{
            model.addAttribute("failureMessage", error);
        }

        return "result";
    }


    @GetMapping("/home/view-file/{id}")
    public String viewFile(@PathVariable("id") Integer id, HttpServletResponse httpServletResponse) throws IOException {
        File file = fileService.getFileById(id);
        httpServletResponse.setContentType(file.getcontenttype());
        httpServletResponse.setContentLength(Integer.valueOf(file.getfilesize()));
        httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + file.getfilename() +"\"");

        FileCopyUtils.copy(file.getfiledata(), httpServletResponse.getOutputStream());

        return "result";
    }



    @PostMapping("/home/delete-file/{id}")
    public String deleteFile(@PathVariable("id") Integer id, @ModelAttribute("credential") Credential credential, @ModelAttribute("notes") Notes notes, Model model, Authentication authentication) throws IOException{

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


//    @GetMapping("/result")
//    public String result(){
//        return "home";
//    }
}

