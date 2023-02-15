package com.library.libraryspringboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column
    private String name;
    @Column
    private String lname;

    public Author() {

    }

    public String getId() {
        return id;
    }

    public Author(String name, String lname) {
        this.name = name;
        this.lname = lname;
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
}
