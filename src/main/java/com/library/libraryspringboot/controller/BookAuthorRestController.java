package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.service.AuthorService;
import com.library.libraryspringboot.service.BookAuthorService;
import com.library.libraryspringboot.service.BookService;
import dto.AuthorDTO;
import dto.BookDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/bookAuthor")

public class BookAuthorRestController {
    private final BookAuthorService bookAuthorService;
    private BookService bookService;
    private AuthorService authorService;

    public BookAuthorRestController(BookAuthorService bookAuthorService, BookService bookService, AuthorService authorService) {
        this.bookAuthorService = bookAuthorService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/all")
    List<BookAuthor> getAll() {
        return bookAuthorService.getAll();
    }

    @GetMapping("/search")
    List<Object> getAdvancedSearch(@RequestBody SearchCriteria detail) {
        if (detail.isEmpty()) {
            throw new MethodInvalidArgumentException("search inputs are empty");
        }
        return bookAuthorService.findBooksAndAuthorsByCriteria(detail);
    }

//    @GetMapping("/id/{id}")
//    List<BookAuthor> getAuthorByBookId(@PathVariable(required = false) Integer id) {  // uncomment
//            return bookAuthorService.getAuthorByBookId(id);
//
//    }
}
