//package com.udacity.jwdnd.course1.cloudstorage.controller;
//
//
//import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
//import com.udacity.jwdnd.course1.cloudstorage.model.*;
//import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
//import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
//import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
//import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//
//@Controller
//@Component
//@RequestMapping("/files")
//public class FileController {
//
//    @Autowired
//    private FileService fileService;
//    private UserMapper userMapper;
//    private NoteService noteService;
//    private CredentialService credentialService;
//    private UserService userService;
//
//    public FileController(FileService fileService, UserMapper userMapper, NoteService noteService, CredentialService credentialService, UserService userService) {
//        this.fileService = fileService;
//        this.userMapper = userMapper;
//        this.noteService = noteService;
//        this.credentialService = credentialService;
//        this.userService = userService;
//    }
//
//    public UserMapper getUserMapper() {
//        return userMapper;
//    }
//
//    public UserService getUserService() {
//        return userService;
//    }
//
//    @PostMapping("/upload")
//    public String uploadFile(
//
//            @RequestParam("newFile")FileForm newFile,
//            @RequestParam MultipartFile file,
//            @RequestParam("newNote") NoteForm newNote,
//            @RequestParam("newCredential") CredentialForm newCredential,
//            Authentication authentication,
//            Model model) {
//
//        // Amber
//
//        String username = authentication.getName();
//        Integer userid = userService.getUser(username).getuserid();
//        String[] fileListings = (String[]) fileService.getFileListings(userid);
//        MultipartFile multipartFile = newFile.getFile();
//        String filename = multipartFile.getOriginalFilename();
//
//        if (multipartFile.isEmpty()) {
//            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
//        }
//
//        if (!this.fileService.isfilenameAvailableForUser(username, filename)) {
//            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
//        }
//        try {
//            this.fileService.saveFile(multipartFile, username);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/home";
//    }
//
//    @GetMapping(value = "/view/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public @ResponseBody
//    List<File> getFile(@PathVariable Integer userid){
//        return fileService.getUploadedFiles(userid);
//    }
//
//
//    @DeleteMapping("/delete")
//    public String deleteFile(
//            @RequestParam(required = false, name = "fileid") Integer fileid) {
//        boolean isSuccess = this.fileService.deleteFile(fileid);
//        return "redirect:/result?isSuccess=" + isSuccess;
//    }
//
//    @GetMapping("/download")
//    public ResponseEntity<InputStreamResource> downloadFile(
//            @RequestParam(required = false, name = "fileid") Integer fileid){
//
//        File file = this.fileService.getFileById(fileid);
//
//        String filename = file.getfilename();
//        String contenttype = file.getcontenttype();
//
//        byte[] filedata = file.getfiledata();
//
//        InputStream inputStream = new ByteArrayInputStream(filedata);
//
//        InputStreamResource resource = new InputStreamResource(inputStream);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
//                .contenttype(MediaType.parseMediaType(contenttype))
//                .body(resource);
//    }
//
//}
//
//
//
//
//
//
//
//
///*
//    @PostMapping("/upload")
//    public String uploadFile(Model model, @RequestParam("fileUpload")MultipartFile file, Authentication authentication,
//                             @ModelAttribute("note")NoteForm noteForm, @ModelAttribute("credential") CredentialForm credentialForm){
//        System.out.println("postFile" + file);
//        String username = authentication.getName();
//        int userid = userMapper.getUser(username).getuserid();
//        if(file.isEmpty()){
//            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
//        } else {
//            File fileObj = new File();
//            fileObj.setcontenttype(file.getcontenttype());
//            fileObj.setfilename(file.getName());
//            fileObj.setuserid(userid);
//            fileObj.setfilesize(file.getSize() + "");
//
//            try {
//                fileObj.setfiledata(fileObj.getfilesize().getBytes());
//                fileService.createFile(fileService.getFileById(userid));
//                model.addAttribute("success", "File uploaded!");
//            } catch (FileAlreadyExistsException e) {
//                e.printStackTrace();
//                model.addAttribute("errorMessage", "File already exists!");
//            }
//        }
//        model.addAttribute("tab", "nav-files-tab");
//        model.addAttribute("file", fileService.getUploadedFiles(userid));
//        model.addAttribute("notes", noteService.getUserNote(userid));
//        model.addAttribute("credentials", credentialService.getUserCredentials(userid));
//
//        return "home";
//
//    }
//
//
//    @RequestMapping(value = {"/files/{id}"}, method = RequestMethod.GET)
//    public ResponseEntity<byte[]> viewFile(@PathVariable(name = "id") String id,
//                                           HttpServletResponse response, HttpServletRequest request) {
//        File file = fileService.getFileById(Integer.parseInt(id));
//        byte[] fileContents = file.getfiledata();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setcontenttype(MediaType.parseMediaType(file.getcontenttype()));
//        String filename = file.getfilename();
//        httpHeaders.setContentDispositionFormData(filename, filename);
//        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        ResponseEntity<byte[]> serverResponse = new ResponseEntity<byte[]>(fileContents, httpHeaders, HttpStatus.OK);
//        return serverResponse;
//    }
//
//    @RequestMapping(value = "files/delete/{id}")
//    private String deleteFile(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
//        System.out.println("deleteFile" + id);
//        fileService.deleteFile(Integer.parseInt(id));
//        redirectAttributes.addFlashAttribute("tab", "nav-files-tab");
//        redirectAttributes.addFlashAttribute("success", true);
//        return "redirect:/home";
//    }
//}
//
// */
