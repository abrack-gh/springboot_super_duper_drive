package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userid;
    private byte[] fileData;

    public File(Integer fileId, String fileName, String contentType, String fileSize, Integer userid, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userid = userid;
        this.fileData = fileData;
    }

    public File() {

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

    public Integer getuserid() {
        return userid;
    }

    public void setuserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getfileData() {
        return fileData;
    }

    public void setfileData(byte[] fileData) {
        this.fileData = fileData;
    }
}