package com.udacity.jwdnd.course1.cloudstorage.model;


import org.springframework.web.multipart.MultipartFile;

public class FileForm {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private Byte[] fileData;
    private MultipartFile file;

    public FileForm(Integer fileId, String fileName, String contentType, String fileSize, Integer userId, Byte[] fileData, MultipartFile file) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
        this.file = file;
    }

    public Integer getfileId() {
        return fileId;
    }

    public void setfileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getfileName() {
        return fileName;
    }

    public void setfileName(String fileName) {
        this.fileName = fileName;
    }

    public String getcontentType() {
        return contentType;
    }

    public void setcontentType(String contentType) {
        this.contentType = contentType;
    }

    public String getfileSize() {
        return fileSize;
    }

    public void setfileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(int userId) {
        this.userId = userId;
    }

    public Byte[] getfileData() {
        return fileData;
    }

    public void setfileData(Byte[] fileData) {
        this.fileData = fileData;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}