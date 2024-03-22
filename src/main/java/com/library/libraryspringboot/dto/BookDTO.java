package com.library.libraryspringboot.dto;

import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor //@TODO: remove if not necessary
public class BookDTO {
    @Null(message = "The ID field must be null in the POST request")
    @NotNull(message = "The ID field must not be null in the PUT request", groups = {PutValidation.class})
    private String id;
    @NotBlank(message = "Title is required", groups = {Default.class, PutValidation.class})
    private String title;
    @NotNull(message = "Year is required", groups = {Default.class, PutValidation.class}) //NOTE:cannot be nullable,because ISBN is required
    private Integer published;
    @NotBlank(message = "ISBN is required", groups = {Default.class, PutValidation.class})
    @Size(min = 13, max = 13, groups = {Default.class, PutValidation.class})
    private String ISBN;
    private List<Integer> authorId;

    public BookDTO(Book book) {
        this.id = String.valueOf(book.getId());
        this.title = book.getTitle();
        this.published = book.getPublished();
        this.ISBN = book.getISBN();
    }

    public BookDTO(Book book, List<Integer> authorId) {
        this.id = String.valueOf(book.getId());
        this.title = book.getTitle();
        this.published = book.getPublished();
        this.ISBN = book.getISBN();
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return String.format("[title=%s,published=%s,ISBN=%s]", title, published, ISBN);
    }

}
