package com.library.libraryspringboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
    //@TODO: add id, add @annotations
    @Id
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private int year;
    @Column
    private String ISBN;  //@TODO: change to char?

    public Book() {

    }

    public Book(String name, int year, String ISBN) {
        this.name = name;
        this.year = year;
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
