package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import com.library.libraryspringboot.service.BookAuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ctable")

public class BookAuthorRestController {
    private final BookAuthorService bookAuthorService;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookAuthorRestController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping
    List<BookAuthor> getAll() {
        return bookAuthorService.getAll();
    }

//    @GetMapping("/id/{id}")
//    List<BookAuthor> getAuthorByBookId(@PathVariable(required = false) Integer id) {  // uncomment
//            return bookAuthorService.getAuthorByBookId(id);
//
//    }
}
