package com.udacity.jwdnd.course1.cloudstorage.model;

import java.io.InputStream;

public class File {
    private Integer fileid;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    public File(Integer fileid, String filename, String contenttype, String filesize, Integer userid, byte[] filedata) {
        this.fileid = fileid;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public File(){

    }

    public File(Integer fileid, String originalFilename, String contenttype, String filesize, int userid, InputStream inputStream) {

    }

    public Integer getfileid() {
        return fileid;
    }

    public void setfileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getfilename() {
        return filename;
    }

    public void setfilename(String filename) {
        this.filename = filename;
    }

    public String getcontenttype() {
        return contenttype;
    }

    public void setcontenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getfilesize() {
        return filesize;
    }

    public void setfilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getuserid() {
        return userid;
    }

    public void setuserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getfiledata() {
        return filedata;
    }

    public void setfiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}