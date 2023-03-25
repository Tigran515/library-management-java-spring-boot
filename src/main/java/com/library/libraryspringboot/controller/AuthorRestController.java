package com.library.libraryspringboot.controller;

//import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;

//import io.swagger.v3.oas.annotations.
import dto.AuthorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
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
    @Operation(summary = "Get authors", description = "Get a list of authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the authors")
    })
    @GetMapping("/all")
    List<AuthorDTO> get() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/id/{id}")
    Optional<AuthorDTO> getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }
//
//    @GetMapping("/search/{detail}")
//    List<AuthorDTO> getByDetail(@PathVariable(required = false) String detail) { //which one to use ? ⬇
//        if (detail == null || detail.isBlank()) {
//            System.err.println("null"); // error message is alerted
//        }
//        return authorService.findAuthorsByDetails(detail);
//    }

//    @GetMapping("/search")
//    List<AuthorDTO> getByDetail(@RequestBody SearchCriteria detail) { // which one to use ? (probably this one) ⬆
////        String search = name.getName();
////        if (search == null || search.isBlank()) {
////            System.out.println("null"); // error message is alerted
////        } @TODO: add validation
//        return authorService.findAuthorsByDetails(detail.getAuthorName(),detail.getAuthorLname(),detail.getAuthorSname());
//    }

    @PostMapping("/add")
    Author post(@RequestBody @Valid AuthorDTO authorDTO) { // In Spring Boot, by default, unknown properties in JSON requests are simply ignored, and no exception is thrown.
        return authorService.addAuthor(authorDTO);
    }

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

}