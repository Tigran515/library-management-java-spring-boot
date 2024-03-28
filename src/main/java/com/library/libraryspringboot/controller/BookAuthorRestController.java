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

    //
//    @GetMapping("/search")
//    <T> T getAdvancedSearch(@RequestBody SearchCriteria detail) {
////        if (detail.isEmpty()) {
////            throw new MethodInvalidArgumentException("search inputs are empty");
////        }
//        return bookAuthorService.findBooksAndAuthorsByCriteria(detail);
//    }

    @GetMapping("/search") // /search/advanced
    <T> T searchBooksAndAuthors(@RequestParam(value = "authorName", required = false) String authorName,
                                @RequestParam(value = "authorLname", required = false) String authorLname,
                                @RequestParam(value = "authorSname", required = false) String authorSname,
                                @RequestParam(value = "bookTitle", required = false) String bookTitle,
                                @RequestParam(value = "isbn", required = false) String isbn) {
        //NOTE: V1 here can be if statement to check whether the detail is null
//        if (detail != null) { //preferred version
//            SearchCriteria searchCriteria = new SearchCriteria();
//            searchCriteria.setDetail(detail);
//            return bookAuthorService.findBooksAndAuthorsByCriteria(searchCriteria);
//        }

        SearchCriteria searchCriteria = new SearchCriteria(authorName, authorLname, authorSname, bookTitle, isbn);
        return bookAuthorService.findBooksAndAuthorsByCriteria(searchCriteria);
    }


    //V2
    @GetMapping("/search/{term}")// /search/basic
    <T> T searchBooksAndAuthorsByTerm(@PathVariable String term) {
        return bookAuthorService.findBooksAndAuthorsByCriteria(new SearchCriteria(term));
    }

}
