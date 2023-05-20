package com.library.libraryspringboot.service;

import dto.BookAuthorDTO;
import dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface BookService {
    Page<BookDTO> getBooks(final Integer pageNumber, final Integer size); // gets all books names only for rendering in the page
    Optional<BookDTO> getBookById(@PathVariable Integer id);
    BookAuthorDTO addBook(BookDTO bookDTO, Integer authorId);
    void deleteBookById(@PathVariable Integer id);
//    BookAuthor saveBook(BookDTO book);
//    List<BookDTO> findBooksByDetails(String title,String ISBN);
}
