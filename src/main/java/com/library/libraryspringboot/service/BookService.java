package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import dto.BookDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    //    Book createBook(String name, int year, String isbn, int authorId);
    List<BookDTO> getAllBooks(); // gets all books names only for rendering in the page
    Optional<BookDTO> getBookById(@PathVariable Integer id);
    Book addBook(BookDTO book);
    void deleteBookById(@PathVariable Integer id);
}
