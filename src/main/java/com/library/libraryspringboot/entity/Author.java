package com.library.libraryspringboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lname;
    private String sname;
    private Integer born;

    public Author() {

    }

    public Integer getId() {
        return id;
    }

    public Author(String name, String lname, String sname, Integer born) {
        this.name = name;
        this.lname = lname;
        this.sname = sname;
        this.born = born;
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

    public Integer getBorn() {
        return born;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }
}
