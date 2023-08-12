package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.dto.AuthorDTO;
import com.library.libraryspringboot.tool.AuthorSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import com.library.libraryspringboot.service.AuthorService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/authors")
public class AuthorRestController {
    private final AuthorService authorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRestController.class);
    private final HttpServletRequest HTTP_SERVLET_REQUEST;

    public AuthorRestController(AuthorService authorService, HttpServletRequest HTTP_SERVLET_REQUEST) {
        this.authorService = authorService;
        this.HTTP_SERVLET_REQUEST = HTTP_SERVLET_REQUEST;
    }

    @Operation(summary = "Get authors", description = "Get a list of authors (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the authors")
    })

    @GetMapping
    public Page<AuthorDTO> getAuthors(
            @RequestParam(defaultValue = "0") final Integer offset,
            @RequestParam(defaultValue = "200") final Integer limit
    ) {
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return authorService.getAuthors(offset, limit);
    }

    @Operation(summary = "Get author by ID", description = "Get an author by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the author by ID")
    })

    @GetMapping("/author/{id}")
    Optional<AuthorDTO> getAuthorById(@PathVariable @Valid @Positive(message = "author id should be positive") Integer id) { //@TODO: make validation work here!
        LOGGER.info("Incoming HTTP GET request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return authorService.getAuthorById(id);
    }

    @Operation(summary = "Add an author", description = "Add a new author data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new author data has been added")
    })

    @PostMapping("/post")
    AuthorDTO post(@RequestBody @Valid AuthorDTO authorDTO) { // In Spring Boot, by default, unknown properties in JSON requests are simply ignored, and no exception is thrown.
        LOGGER.info("Incoming HTTP POST request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return authorService.addAuthor(authorDTO);
    }

    @Operation(summary = "Update an author", description = "Update an existing author's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "author data has been updated")
    })
    @PatchMapping("/update/{id}")
    AuthorDTO patch(@PathVariable Integer id, @RequestBody AuthorDTO updatedAuthorDTO) { //@TODO: rename to actual functionality name
        LOGGER.info("Incoming HTTP PATCH request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        return authorService.updateAuthorFields(updatedAuthorDTO, id);
    }

    @Operation(summary = "Delete an author", description = "Delete an author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "author deleted")
    })

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) { ///@TODO add validation here
        LOGGER.info("Incoming HTTP DELETE request to [URL={}]", HTTP_SERVLET_REQUEST.getRequestURL().toString());
        authorService.deleteAuthorById(id);
    }

}
