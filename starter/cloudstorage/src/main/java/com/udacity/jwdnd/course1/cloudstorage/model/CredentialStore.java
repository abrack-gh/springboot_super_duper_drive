package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.stereotype.Service;

public class CredentialStore {
    private Integer credentialid;
    private Integer userid;
    private String url;
    private String username;
    private String password;
    private String key;
    private String decryptPassword;

    public CredentialStore() {

    }

    public CredentialStore(
            Integer credentialid,
            Integer userid,
            String url,
            String username,
            String key,
            String password) {

        this.credentialid = credentialid;
        this.userid = userid;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

    public Integer getcredentialid() {
        return credentialid;
    }

    public void setcredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public Integer getuserid() {
        return userid;
    }

    public void setuserid(Integer userid) {
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDecryptPassword() {
        return decryptPassword;
    }

    public void setDecryptPassword(String decryptPassword) {
        this.decryptPassword = decryptPassword;
    }
}