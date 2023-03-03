package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class BookAuthorId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author authorId;

    public BookAuthorId() {
    }

    public BookAuthorId(Book bookId, Author authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (!(obj instanceof BookAuthorId)) return false;
//        BookAuthorId comp = (BookAuthorId) obj;
//        if (!getBookId().equals(comp.getBookId())) return false;
//        return getAuthorId().equals(comp.getAuthorId());
//    }
//
//    @Override
//    public int hashCode() {
//        int result = getBookId().hashCode();
//        result = 31 * result + getAuthorId().hashCode();
//        return result;
//    }
}
