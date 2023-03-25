package com.library.libraryspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.libraryspringboot.Tool.BookAuthorId;
import jakarta.persistence.*;
import org.springframework.data.relational.core.sql.In;

@Entity
@Table(name = "book_author")
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId bookAuthorId;

    public BookAuthor() {
    }

    public BookAuthorId getBookAuthorId() {
        return bookAuthorId;
    }

//    public BookAuthor(BookAuthorId bookAuthorId) {
//        this.bookAuthorId = bookAuthorId;
//    }
}