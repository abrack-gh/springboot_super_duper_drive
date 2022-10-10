package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    public String fileSize;
    private Integer userid;
    private Byte[] fileData;

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Integer getUserid() {
        return userid;
    }

    public Byte[] getFileData() {
        return fileData;
    }
}
