package com.udacity.jwdnd.course1.cloudstorage.model;



public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private Integer key;
    private String password;
    private Integer userid;

    public Credential(Integer credentialId, String url, String username, Integer key, String password, Integer userid){
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public Credential(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public Integer getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
