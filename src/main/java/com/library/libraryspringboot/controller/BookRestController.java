package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.Tool.BookAuthorRequest;
import com.library.libraryspringboot.service.BookService;
import dto.BookAuthorDTO;
import dto.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/books")
public class BookRestController {
    private final BookService bookService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);
    private HttpServletRequest httpServletRequest;

    public BookRestController(BookService bookService, HttpServletRequest httpServletRequest) {
        this.bookService = bookService;
        this.httpServletRequest = httpServletRequest;
    }

    @Operation(summary = "Get books", description = "Get a list of books (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the books")
    })

    @GetMapping
    Page<BookDTO> getBooks(
            @RequestParam(defaultValue = "0") final Integer offset,
            @RequestParam(defaultValue = "200") final Integer limit
    ) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", httpServletRequest.getRequestURL().toString());
        return bookService.getBooks(offset, limit);
    }

    @Operation(summary = "Get book by ID", description = "Get a book by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the book by ID")
    })

    @GetMapping("/book/{id}")
    Optional<BookDTO> getBookById(@PathVariable Integer id) { //@TODO validate the Integer
        LOGGER.info("Incoming HTTP GET request to [URL={}]", httpServletRequest.getRequestURL().toString());
        return bookService.getBookById(id);
    }

    @Operation(summary = "Add a book", description = "Add a new book data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new book data has been added")
    })

    @PostMapping("/post")
    BookAuthorDTO post(@RequestBody @Valid BookAuthorRequest request) {
        LOGGER.info("Incoming HTTP POST request to [URL={}]", this.httpServletRequest.getRequestURL().toString());
        return bookService.addBook(request.getBookDTO(), request.getAuthorId());
    }

    @Operation(summary = "Delete a book", description = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book deleted")
    })

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) { //@TODO validate the Integer
        LOGGER.info("Incoming HTTP DELETE request to [URL={}]", httpServletRequest.getRequestURL().toString());
        bookService.deleteBookById(id);
    }
}
//
