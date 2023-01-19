package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {

    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

    public User(Integer userid, String username, String salt, String password, String firstName, String lastName) {
        this.userid = userid;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public User(String firstName) {
        this.firstName = firstName;

    }


    public User() {

    }

    public Integer getuserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setuserid(Integer userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }
}
