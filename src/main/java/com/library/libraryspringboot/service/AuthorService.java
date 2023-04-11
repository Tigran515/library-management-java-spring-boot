package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Author;
import dto.AuthorDTO;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface AuthorService {
    Page<AuthorDTO> getAllAuthors(final Integer pageNumber, final Integer size);
    Optional<AuthorDTO> getAuthorById(Integer id);
    Author addAuthor(AuthorDTO authorDTO);
    void deleteAuthorById( Integer id);
}
