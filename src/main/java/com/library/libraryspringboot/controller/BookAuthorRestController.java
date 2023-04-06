package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.service.AuthorService;
import com.library.libraryspringboot.service.BookAuthorService;
import com.library.libraryspringboot.service.BookService;
import dto.BookAuthorDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    List<BookAuthorDTO> getAll() {
        return bookAuthorService.getAll();
    }

    @GetMapping("/search")
    <T> T getAdvancedSearch(@RequestBody SearchCriteria detail) {
//        if (detail.isEmpty()) {
//            throw new MethodInvalidArgumentException("search inputs are empty");
//        }
        return bookAuthorService.findBooksAndAuthorsByCriteria(detail);
    }

//    @GetMapping("/id/{id}")
//    List<BookAuthorDTO> getBookAuthorByBookId(@PathVariable Book id) {
//        return bookAuthorService.getBookAuthorByBookId(id);
//    }

//    @GetMapping("/id/{id}")
//    List<BookAuthor> getAuthorByBookId(@PathVariable(required = false) Integer id) {  // uncomment
//            return bookAuthorService.getAuthorByBookId(id);
//
//    }
}
