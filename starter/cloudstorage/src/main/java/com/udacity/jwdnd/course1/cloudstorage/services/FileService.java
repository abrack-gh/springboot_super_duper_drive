package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    @Autowired

    private FileMapper fileMapper;
    private UserMapper userMapper;
    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public Integer saveFile(MultipartFile fileUpload, String username) throws IOException {

        User user = this.userMapper.getUser(username);
        Integer userid = user.getuserid();

        //return fileMapper.insertFile((File) fileUpload);

        return fileMapper.insertFile(new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), Long.toString(fileUpload.getSize()), userid.intValue(), fileUpload.getInputStream()));
    }

    public List<File> getUploadedFiles(Integer userid){
        return fileMapper.getAllFiles(userid);
    }

    public void deleteFile(Integer fileId) {

        this.fileMapper.delete(fileId);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFile(fileId);
    }

    public boolean isfileNameAvailableForUser(String username, String fileName) {

        Map<String, Object> paraMap = new HashMap<>();

        paraMap.put("username", username);
        paraMap.put("fileName", fileName);

        return this.fileMapper.getFileByUsernameAndfileName(paraMap).isEmpty();
    }



}