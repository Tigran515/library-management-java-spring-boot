package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.Tool.BookAuthorRequest;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.service.BookService;
import dto.AuthorDTO;
import dto.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "Get books", description = "Get a list of books (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the books")
    })

    @GetMapping("/all")
    Page<BookDTO> getAllBooks(
            @RequestParam(defaultValue = "0") final Integer pageNumber,
            @RequestParam(defaultValue = "3") final Integer size
    ) {
        return bookService.getAllBooks(pageNumber, size);
    }

    @Operation(summary = "Get book by ID", description = "Get a book by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the book by ID")
    })

    @GetMapping("/id/{id}")
    Optional<BookDTO> getBookById(@PathVariable @NotNull Integer id) { //@NotNull added
        return bookService.getBookById(id);
    }

    @Operation(summary = "Add a book", description = "Add a new book data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new book data has been added")
    })

    @PostMapping("/add")
    BookAuthor post(@RequestBody @Valid BookAuthorRequest request) {
        return bookService.addBook(request.getBookDTO(), request.getAuthorId());
    }

    @Operation(summary = "Delete a book", description = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book deleted")
    })

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }
}