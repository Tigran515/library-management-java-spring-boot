package com.library.libraryspringboot.controller;

//import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;

import dto.AuthorDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.service.AuthorService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/authors")
public class AuthorRestController {
    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    List<AuthorDTO> get() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/id/{id}")
    Optional<AuthorDTO> getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/search/{detail}")
    List<AuthorDTO> getByDetail(@PathVariable(required = false) String detail) { //which one to use ? ⬇
        if (detail == null || detail.isBlank()) {
            System.err.println("null"); // error message is alerted
        }
        return authorService.findAuthorsByDetails(detail);
    }

    //
//    @GetMapping("/search")
//    List<Author> getByDetail(@RequestBody SearchCriteria detail) { // which one to use ? (probably this one) ⬆
//        String search = detail.getDetail();
//        if (search == null || search.isBlank()) {
//            System.out.println("null"); // error message is alerted
//        }
//        return authorService.findAuthorsByDetails(detail.getDetail());
//    }
//
    @PostMapping("/add")
    Author post(@RequestBody @Valid AuthorDTO author) { // In Spring Boot, by default, unknown properties in JSON requests are simply ignored, and no exception is thrown.
        return authorService.addAuthor(author);
    }

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

}