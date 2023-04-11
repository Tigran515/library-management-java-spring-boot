package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.service.AuthorService;
import com.library.libraryspringboot.service.BookAuthorService;
import com.library.libraryspringboot.service.BookService;
import dto.BookAuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/bookAuthor")

public class BookAuthorRestController {
    private final BookAuthorService bookAuthorService;

    public BookAuthorRestController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;

    }

    @GetMapping("/all")
    Page<BookAuthorDTO> getAll(@RequestParam(defaultValue = "0") final Integer pageNumber,
                               @RequestParam(defaultValue = "3") final Integer size) {
        return bookAuthorService.getAllBooksAndAuthors(pageNumber, size);
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
