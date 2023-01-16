package com.udacity.jwdnd.course1.cloudstorage.model;


public class FileForm {
    private int fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private int userId;
    private byte[] fileData;

    public int getfileId() {
        return fileId;
    }

    public void setfileId(int fileId) {
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

    public int getuserId() {
        return userId;
    }

    public void setuserId(int userId) {
        this.userId = userId;
    }

    public byte[] getfileData() {
        return fileData;
    }

    public void setfileData(byte[] fileData) {
        this.fileData = fileData;
    }
}