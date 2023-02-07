package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private int userid;

    public Notes(Integer noteId, String noteTitle, String noteDescription, int userid) {
        this.noteId = noteId;
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

    public Integer getnoteId() {
        return noteId;
    }

    public void setnoteId(Integer noteId) {
        this.noteId = noteId;
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

    public void setUserId(int userid) {
        this.userid = userid;
    }
}
