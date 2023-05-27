package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.service.BookAuthorService;
import dto.BookAuthorDTO;
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
    private HttpServletRequest httpServletRequest;

    public BookAuthorRestController(BookAuthorService bookAuthorService, HttpServletRequest httpServletRequest) {
        this.bookAuthorService = bookAuthorService;
        this.httpServletRequest = httpServletRequest;

    }

    @GetMapping
    Page<BookAuthorDTO> getAll(
            @RequestParam(defaultValue = "0") final Integer offset,
            @RequestParam(defaultValue = "200") final Integer limit) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", httpServletRequest.getRequestURL().toString());
        
        return bookAuthorService.getBooksAndAuthors(offset, limit);
    }

    @GetMapping("/search")
    <T> T getAdvancedSearch(@RequestBody SearchCriteria detail) {
//        if (detail.isEmpty()) {
//            throw new MethodInvalidArgumentException("search inputs are empty");
//        }
        return bookAuthorService.findBooksAndAuthorsByCriteria(detail);
    }
}
