package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.BookAuthor;
import dto.AuthorDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface BookAuthorService {
    List<BookAuthor> getAll();
    List<Object> findBooksAndAuthorsByCriteria(SearchCriteria detail); // advanced search method

    //    Optional<CrossTable> getById(String id);
//    Iterable<Author> getAuthorsByBookId(Integer bookId);
//    List<Author> getAuthorByBookId(@PathVariable Integer bookId);

//    List<BookAuthor> getAuthorByBookId(Integer id); //@TODO uncomment

}
