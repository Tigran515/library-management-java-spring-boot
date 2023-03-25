package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Book;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    //    Book createBook(String name, int year, String isbn, int authorId);
    List<dto.BookDTO> getAllBooks(); // gets all books names only for rendering in the page
    Optional<dto.BookDTO> getBookById(@PathVariable Integer id);
    Book addBook(dto.BookDTO bookDTO);
    void deleteBookById(@PathVariable Integer id);
//    List<BookDTO> findBooksByDetails(String title,String ISBN);
}
