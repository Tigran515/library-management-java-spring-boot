package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.tool.SearchCriteria;
import com.library.libraryspringboot.service.BookAuthorService;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookAuthor")

public class BookAuthorRestController {
    private final BookAuthorService bookAuthorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorRestController.class);
    private final HttpServletRequest HTTP_SERVLET_REQUEST;

    public BookAuthorRestController(BookAuthorService bookAuthorService, HttpServletRequest HTTP_SERVLET_REQUEST) {
        this.bookAuthorService = bookAuthorService;
        this.HTTP_SERVLET_REQUEST = HTTP_SERVLET_REQUEST;

    }

    @GetMapping
    Page<BookAuthorDTO> getAll(
            @RequestParam(defaultValue = "0") final Integer offset,
            @RequestParam(defaultValue = "200") final Integer limit) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());

        return bookAuthorService.getBooksAndAuthors(offset, limit);
    }

    //    @GetMapping("/search")
    @PostMapping("/search")
    <T> T getAdvancedSearch(@RequestBody SearchCriteria detail) {
//        if (detail.isEmpty()) {
//            throw new MethodInvalidArgumentException("search inputs are empty");
//        }
        return bookAuthorService.findBooksAndAuthorsByCriteria(detail);
    }
}
