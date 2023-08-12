package com.library.libraryspringboot.dto;

public class UserInformationUpdateRequest {
   private String name;
   private String lname;
   private String sname;
   private String username;

    public UserInformationUpdateRequest() {
    }

    public UserInformationUpdateRequest(String name, String lname, String sname, String username) {
        this.name = name;
        this.lname = lname;
        this.sname = sname;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
