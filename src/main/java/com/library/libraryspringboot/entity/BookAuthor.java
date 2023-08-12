package com.library.libraryspringboot.entity;

import com.library.libraryspringboot.tool.BookAuthorId;
import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId bookAuthorId;

    public BookAuthor() {
    }

    public BookAuthor(BookAuthorId bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public BookAuthorId getBookAuthorId() {
        return bookAuthorId;
    }


}
