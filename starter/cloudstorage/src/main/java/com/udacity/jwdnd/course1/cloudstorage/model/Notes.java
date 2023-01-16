package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {
    private Integer nodeid;
    private String noteTitle;
    private String noteDescription;
    private int userId;

    public Notes(Integer nodeid, String noteTitle, String noteDescription, int userId) {
        this.nodeid = nodeid;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public Notes(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Notes() {

    }

    public Integer getNodeid() {
        return nodeid;
    }

    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }

    public String getnoteTitle() {
        return noteTitle;
    }

    public void setnoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getnoteDescription() {
        return noteDescription;
    }

    public void setnoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public int getuserId() {
        return userId;
    }

    public void setuserId(int userId) {
        this.userId = userId;
    }
}
