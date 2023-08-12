package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.dto.BookDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BookAuthorRequest { //@TODO: are this type of validation annotations useful here?
    @NotNull(message = "In request the book is empty")
    private BookDTO bookDTO;
    @NotNull
    private List<Integer> authorId;

    public BookAuthorRequest(@NotNull(message = "In request the book is empty") BookDTO bookDTO, @NotNull List<Integer> authorId) {
        this.bookDTO = bookDTO;
        this.authorId = authorId;
    }

    public BookDTO getBookDTO() {
        return bookDTO;
    }

    public List<Integer> getAuthorId() {
        return authorId;
    }
}
