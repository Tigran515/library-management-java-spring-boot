package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.service.BookService;
import dto.BookDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/book")
public class BookRestController {
    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/id/{id}")
    Optional<BookDTO> getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/add")
    Book post(@RequestBody BookDTO book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }
}