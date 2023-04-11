package com.library.libraryspringboot.Tool;

import dto.BookDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public class BookAuthorRequest {
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