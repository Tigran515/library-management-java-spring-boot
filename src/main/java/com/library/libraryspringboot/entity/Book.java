package com.library.libraryspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer published;
    private String ISBN;

    public Book() {

    }

    public Book(String title, Integer published, String ISBN) {
        this.title = title;
        this.published = published;
        this.ISBN = ISBN;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer year) {
        this.published = year;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return String.format("[title=%s,published=%s,ISBN=%s]", title, published, ISBN);
    }
}
