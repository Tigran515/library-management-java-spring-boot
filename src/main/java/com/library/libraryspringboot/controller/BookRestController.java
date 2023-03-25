package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.service.BookService;
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

    @GetMapping("/all")
    List<dto.BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/id/{id}")
    Optional<dto.BookDTO> getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }

//    @GetMapping("/search")
//    List<BookDTO> getByDetail(@RequestBody SearchCriteria detail) {
//        //@TODO: add validation
//        return bookService.findBooksByDetails(detail.getBookTitle(), detail.getISBN());
//    }

    @PostMapping("/add")
    Book post(@RequestBody dto.BookDTO bookDTO) {
        return bookService.addBook(bookDTO);
    }

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }
}