package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public void createFile(File file) throws FileAlreadyExistsException {
        if(this.fileMapper.getFile(file.getUserid(), String.valueOf(file.getFilename().isEmpty()))) {
            this.fileMapper.storeFile(file);
        }
    }

    public List<File> getUploadedFiles(Integer userid){
        return fileMapper.getAllFiles(userid);
    }

    public int deleteFile(int fileId) {
        return this.fileMapper.deleteFile(fileId);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileById(fileId);
    }

    public Object getFileListings(Integer userId) {
        return this.fileMapper.getFileById(userId);
    }

    public Object getUserFiles(int userId) {
        return this.fileMapper.getAllFiles(userId);
    }

    public Object getUserFiles(int userId) {
        return this.fileMapper.getAllFiles(userId);
    }
}