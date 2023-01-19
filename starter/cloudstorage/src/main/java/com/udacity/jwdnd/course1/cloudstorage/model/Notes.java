package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {
    private Integer nodeid;
    private String noteTitle;
    private String noteDescription;
    private int userid;

    public Notes(Integer nodeid, String noteTitle, String noteDescription, int userid) {
        this.nodeid = nodeid;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
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

    public int getuserid() {
        return userid;
    }

    public void setuserid(int userid) {
        this.userid = userid;
    }
}
