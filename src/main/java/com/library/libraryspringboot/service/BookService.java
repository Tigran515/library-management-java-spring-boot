package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.BookAuthorDTO;
import com.library.libraryspringboot.dto.BookDTO;
import com.library.libraryspringboot.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookService {
    // Are validation annotations needed here ?
    Page<BookDTO> getBooks(final Integer pageNumber, final Integer size); // gets all books names only for rendering in the page
    BookDTO getBookById(@PathVariable Integer id);
    List<BookAuthorDTO> addBook(BookDTO bookDTO);
    void deleteBookById(@PathVariable Integer id);
    List<BookAuthorDTO> updateBookById(BookDTO bookDTO);
//    List<BookDTO> findBooksByDetails(String title,String ISBN);
}
