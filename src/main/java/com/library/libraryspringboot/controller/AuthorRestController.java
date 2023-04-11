package com.library.libraryspringboot.controller;

import dto.AuthorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.service.AuthorService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/authors")
public class AuthorRestController {
    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Get authors", description = "Get a list of authors (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the authors")
    })

    public Page<AuthorDTO> getAllAuthors(
            @RequestParam(defaultValue = "0") final Integer pageNumber,//offset
            @RequestParam(defaultValue = "200") final Integer size//limit
    ) {
        return authorService.getAllAuthors(pageNumber, size);
    }

    @Operation(summary = "Get author by ID", description = "Get an author by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the author by ID")
    })

    @GetMapping("/author/{id}")
    Optional<AuthorDTO> getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @Operation(summary = "Add an author", description = "Add a new author data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new author data has been added")
    })

    @PostMapping("/add")
    Author post(@RequestBody @Valid AuthorDTO authorDTO) { // In Spring Boot, by default, unknown properties in JSON requests are simply ignored, and no exception is thrown.
        return authorService.addAuthor(authorDTO);
    }

    @Operation(summary = "Delete an author", description = "Delete an author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "author deleted")
    })

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

}