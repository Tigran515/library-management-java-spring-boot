package com.library.libraryspringboot.Tool;

import dto.BookDTO;
import jakarta.validation.constraints.NotNull;

public class BookAuthorRequest { //@TODO: are this type of validation annotations useful here?
    @NotNull(message = "In request the book is empty")
    private BookDTO bookDTO;
    @NotNull
    private Integer authorId;

    public BookAuthorRequest(BookDTO bookDTO, Integer authorId) {
        this.bookDTO = bookDTO;
        this.authorId = authorId;
    }

    public BookDTO getBookDTO() {
        return bookDTO;
    }

    public Integer getAuthorId() {
        return authorId;
    }

}