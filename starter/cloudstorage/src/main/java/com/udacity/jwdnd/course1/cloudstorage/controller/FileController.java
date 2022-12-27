package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    private UserMapper userMapper;
    private NoteService noteService;
    private CredentialService credentialService;



    public FileController(FileService fileService, UserMapper userMapper, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @PostMapping("/file")
    public String uploadFile(Model model, @RequestParam("fileUpload")MultipartFile file, Authentication authentication,
                             @ModelAttribute("note")NoteForm noteForm, @ModelAttribute("credential") CredentialForm credentialForm){
        System.out.println("postFile" + file);
        String username = authentication.getName();
        int userId = userMapper.getUser(username).getUserId();
        if(file.isEmpty()){
            model.addAttribute("errorMessage", "No file selected");
        } else {
            File fileObj = new File();
            fileObj.setContenttype(file.getContentType());
            fileObj.setFilename(file.getName());
            fileObj.setUserid(userId);
            fileObj.setFilesize(file.getSize() + "");

            try {
                fileObj.setFiledata(fileObj.getFilesize().getBytes());
                fileService.createFile(fileService.getFileById(userId));
                model.addAttribute("success", "File uploaded!");
            } catch (FileAlreadyExistsException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "File already exists!");
            }
        }
        model.addAttribute("tab", "nav-files-tab");
        model.addAttribute("file", fileService.getUploadedFiles(userId));
        model.addAttribute("notes", noteService.getUserNote(userId));
        model.addAttribute("credentials", credentialService.getUserCredentials(userId));

        return "home";

    }


    @RequestMapping(value = {"/files/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable(name = "id") String id,
                                           HttpServletResponse response, HttpServletRequest request) {
        File file = fileService.getFileById(Integer.parseInt(id));
        byte[] fileContents = file.getFiledata();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(file.getContenttype()));
        String fileName = file.getFilename();
        httpHeaders.setContentDispositionFormData(fileName, fileName);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> serverResponse = new ResponseEntity<byte[]>(fileContents, httpHeaders, HttpStatus.OK);
        return serverResponse;
    }

    @RequestMapping(value = "files/delete/{id}")
    private String deleteFile(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        System.out.println("deleteFile" + id);
        fileService.deleteFile(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("tab", "nav-files-tab");
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }
}

