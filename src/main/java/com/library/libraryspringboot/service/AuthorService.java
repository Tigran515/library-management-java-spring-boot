package com.library.libraryspringboot.service;

import com.library.libraryspringboot.model.Author;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AuthorService {
    List<Author> get();
    Author addAuthor(Author author);
    List<Author> getAuthorByName(String name);
    void deleteAuthorByName(@PathVariable String name); // TODO: add @RequestBody

    void deleteAuthor(@PathVariable String name,@PathVariable String lname);
}
