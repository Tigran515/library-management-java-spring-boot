package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.tool.SearchCriteria;
import com.library.libraryspringboot.service.BookAuthorService;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookAuthor") //should be plural

public class BookAuthorRestController {
    private final BookAuthorService bookAuthorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorRestController.class);
    private final HttpServletRequest HTTP_SERVLET_REQUEST;

    public BookAuthorRestController(BookAuthorService bookAuthorService, HttpServletRequest HTTP_SERVLET_REQUEST) {
        this.bookAuthorService = bookAuthorService;
        this.HTTP_SERVLET_REQUEST = HTTP_SERVLET_REQUEST;
    }

    @Operation(summary = "Get all books with authors", description = "Get a list of books with authors (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the booksAuthors")
    })
    @GetMapping
    Page<BookAuthorDTO> getAll(
            @RequestParam(defaultValue = "0") final Integer offset,
            @RequestParam(defaultValue = "200") final Integer limit) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return bookAuthorService.getBooksAndAuthors(offset, limit);
    }

    @Operation(summary = "Get advanced search results", description = "Get all matches results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the booksAuthors search results")
    })
    @GetMapping("/search")
    <T> T searchBooksAndAuthors(@RequestParam(value = "authorName", required = false) String authorName,
                                @RequestParam(value = "authorLname", required = false) String authorLname,
                                @RequestParam(value = "authorSname", required = false) String authorSname,
                                @RequestParam(value = "bookTitle", required = false) String bookTitle,
                                @RequestParam(value = "isbn", required = false) String isbn) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        SearchCriteria searchCriteria = new SearchCriteria(authorName, authorLname, authorSname, bookTitle, isbn);
        return bookAuthorService.findBooksAndAuthorsByCriteria(searchCriteria);
    }

    @Operation(summary = "Get search by term results", description = "Get term search results")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the booksAuthors")
    })
    @GetMapping("/search/{term}")
    <T> T searchBooksAndAuthorsByTerm(@PathVariable String term) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return bookAuthorService.findBooksAndAuthorsByCriteria(new SearchCriteria(term));
    }

}
