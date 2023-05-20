package com.library.libraryspringboot.controller;

import dto.AuthorDTO;
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
    private HttpServletRequest httpServletRequest;

    public AuthorRestController(AuthorService authorService, HttpServletRequest httpServletRequest) {
        this.authorService = authorService;
        this.httpServletRequest = httpServletRequest;
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
        String requestUrl = httpServletRequest.getRequestURL().toString();
        LOGGER.info("Incoming HTTP GET request to [URL={}]", requestUrl);
        return authorService.getAuthors(offset, limit);
    }

    @Operation(summary = "Get author by ID", description = "Get an author by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the author by ID")
    })

    @GetMapping("/author/{id}")
    Optional<AuthorDTO> getAuthorById(@PathVariable @Valid @Positive(message = "author id should be positive") Integer id) { //@TODO: make validation work here!
        String requestUrl = httpServletRequest.getRequestURL().toString();
        LOGGER.info("Incoming HTTP GET request to [URL={}]", requestUrl);
        return authorService.getAuthorById(id);
    }

    @Operation(summary = "Add an author", description = "Add a new author data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new author data has been added")
    })

    @PostMapping("/post")
    AuthorDTO post(@RequestBody @Valid AuthorDTO authorDTO) { // In Spring Boot, by default, unknown properties in JSON requests are simply ignored, and no exception is thrown.
        String requestUrl = httpServletRequest.getRequestURL().toString();
        LOGGER.info("Incoming HTTP POST request to [URL={}]", requestUrl);
        return authorService.addAuthor(authorDTO);
    }

    @Operation(summary = "Delete an author", description = "Delete an author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "author deleted")
    })

    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) { ///@TODO add validation here
        String requestUrl = httpServletRequest.getRequestURL().toString();
        LOGGER.info("Incoming HTTP DELETE request to [URL={}]", requestUrl);
        authorService.deleteAuthorById(id);
    }
}
