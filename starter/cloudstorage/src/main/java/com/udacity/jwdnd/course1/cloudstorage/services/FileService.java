package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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

    public boolean saveFile(MultipartFile file, String username) throws IOException {

        User user = this.userMapper.getUser(username);

        Integer userId = user.getuserId();

        byte[] fileData = file.getBytes();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        String fileName = file.getOriginalFilename();

        this.fileMapper.insert(new File(null, fileName, contentType, fileSize, userId, fileData));

        return true;
    }

    public List<File> getUploadedFiles(){
        return fileMapper.getAllFiles();
    }

    public boolean deleteFile(Integer fileId) {

        this.fileMapper.delete(fileId);

        return true;
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileById(fileId);
    }

    public File getFile(String fileName){
        return fileMapper.getFile(fileName);
    }

    public Object getFileListings(Integer userId) {
        return this.fileMapper.getFileById(userId);
    }

    public boolean isfileNameAvailableForUser(String username, String fileName) {

        Map<String, Object> paraMap = new HashMap<>();

        paraMap.put("username", username);
        paraMap.put("fileName", fileName);

        return this.fileMapper.getFileByUsernameAndfileName(paraMap).isEmpty();
    }



}