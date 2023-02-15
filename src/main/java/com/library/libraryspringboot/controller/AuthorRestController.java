package com.library.libraryspringboot.controller;

import com.library.libraryspringboot.model.Author;
import com.library.libraryspringboot.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/authors")
public class AuthorRestController {
    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    List<Author> get() {
        return authorService.get();  // I think DTO is not needed
    }

    @PostMapping("/add")
    Author post(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }


    @DeleteMapping("/delete/{fullName}")
    void deleteAuthor(@PathVariable String fullName) {
        String[] split = fullName.split("_");
        String name = split[0];
        String lname = split[1];
        authorService.deleteAuthor(name,lname);
    }

}
