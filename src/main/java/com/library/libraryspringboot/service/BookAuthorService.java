package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.BookAuthor;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface BookAuthorService {
    List<BookAuthor> getAll();
    //    Optional<CrossTable> getById(String id);
//    Iterable<Author> getAuthorsByBookId(Integer bookId);
//    List<Author> getAuthorByBookId(@PathVariable Integer bookId);

//    List<BookAuthor> getAuthorByBookId(Integer id); //@TODO uncomment

}
