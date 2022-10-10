package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    private String credentialid;
    private String password;
    private String url;
    private String username;

    public CredentialForm(String credentialid, String password, String url, String username) {
        this.credentialid = credentialid;
        this.password = password;
        this.url = url;
        this.username = username;
    }

    public String getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(String credentialid) {
        this.credentialid = credentialid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
