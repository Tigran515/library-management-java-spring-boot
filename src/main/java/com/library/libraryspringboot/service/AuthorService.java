package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Author;
import dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDTO> getAllAuthors();
    Optional<AuthorDTO> getAuthorById( Integer id);
    Author addAuthor(AuthorDTO author);
    void deleteAuthorById( Integer id);
    List<AuthorDTO> findAuthorsByDetails(String detail);
}