package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.service.BookService;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import com.library.libraryspringboot.dto.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookRestController {
    private final BookService bookService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);
    private final HttpServletRequest HTTP_SERVLET_REQUEST;

    public BookRestController(BookService bookService, HttpServletRequest HTTP_SERVLET_REQUEST) {
        this.bookService = bookService;
        this.HTTP_SERVLET_REQUEST = HTTP_SERVLET_REQUEST;
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
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return bookService.getBooks(offset, limit);
    }

    @Operation(summary = "Get book by ID", description = "Get a book by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the book by ID")
    })
    @GetMapping("/book/{id}")
    BookDTO getBookById(@PathVariable @NotNull Integer id) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return bookService.getBookById(id);
    }

    @Operation(summary = "Add a book", description = "Add a new book data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new book data has been added")
    })
    @PostMapping("/post")
    List<BookAuthorDTO> post(@RequestBody @Valid BookDTO bookDTO) {///@Valid
        LOGGER.info("Incoming HTTP POST request to [URL={}]", this.HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return bookService.addBook(bookDTO);
    }

    @Operation(summary = "Update a book", description = "Update Book information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "a book has been updated")
    })
    @PutMapping("/update")
    List<BookAuthorDTO> put(@RequestBody @Validated(PutValidation.class) BookDTO bookDTO) {
        LOGGER.info("Incoming HTTP PUT request to [URL={}]", this.HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return bookService.updateBookById(bookDTO);
    }

    @Operation(summary = "Delete a book", description = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "book deleted")
    })
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable @NotNull Integer id) {
        LOGGER.info("Incoming HTTP DELETE request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        bookService.deleteBookById(id);
    }

}
