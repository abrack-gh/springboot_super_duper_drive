package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@Component
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;


    public FileController() {

    }

    public FileController(FileService fileService) {
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

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile fileUpload,
            Authentication authentication
    ) {
        String username = (String) authentication.getPrincipal();

        if (fileUpload.isEmpty()) {
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        }

        String fileName = fileUpload.getOriginalfileName();

        if (!this.fileService.isfileNameAvailableForUser(username, fileName)) {
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        }
        try {
            this.fileService.saveFile(fileUpload, username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/result?isSuccess=" + true;
    }

    @GetMapping("/delete")
    public String deleteFile(
            @RequestParam(required = false, name = "fileId") Integer fileId) {
        boolean isSuccess = this.fileService.deleteFile(fileId);
        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam(required = false, name = "fileId") Integer fileId){

        File file = this.fileService.getFileById(fileId);

        String fileName = file.getfileName();
        String contentType = file.getcontentType();

        byte[] fileData = file.getfileData();

        InputStream inputStream = new ByteArrayInputStream(fileData);

        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}








/*
    @PostMapping("/upload")
    public String uploadFile(Model model, @RequestParam("fileUpload")MultipartFile file, Authentication authentication,
                             @ModelAttribute("note")NoteForm noteForm, @ModelAttribute("credential") CredentialForm credentialForm){
        System.out.println("postFile" + file);
        String username = authentication.getName();
        int userId = userMapper.getUser(username).getuserId();
        if(file.isEmpty()){
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        } else {
            File fileObj = new File();
            fileObj.setcontentType(file.getcontentType());
            fileObj.setfileName(file.getName());
            fileObj.setuserId(userId);
            fileObj.setfileSize(file.getSize() + "");

            try {
                fileObj.setfileData(fileObj.getfileSize().getBytes());
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
        byte[] fileContents = file.getfileData();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setcontentType(MediaType.parseMediaType(file.getcontentType()));
        String fileName = file.getfileName();
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

 */
