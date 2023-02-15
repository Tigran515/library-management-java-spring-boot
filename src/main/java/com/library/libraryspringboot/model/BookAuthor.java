package com.library.libraryspringboot.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Column;

@Entity
@Table(name = "cross_table")
public class BookAuthor {
    @Id
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


        public BookAuthor() {
    }

    public BookAuthor(Book book, Author author) {
        this.book = book;
        this.author = author;
    }
}