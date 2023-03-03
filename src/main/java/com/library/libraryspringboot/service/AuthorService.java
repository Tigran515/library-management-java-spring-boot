package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Author;
import dto.AuthorDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDTO> getAllAuthors();
    Optional<AuthorDTO> getAuthorById(@PathVariable Integer id);
    Author addAuthor(AuthorDTO author);
    void deleteAuthorById(@PathVariable Integer id);
    List<AuthorDTO> findAuthorsByDetails(@PathVariable String detail);
}
