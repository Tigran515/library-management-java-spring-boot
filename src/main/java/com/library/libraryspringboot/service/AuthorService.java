package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.AuthorDTO;
import org.springframework.data.domain.Page;

public interface AuthorService {
    Page<AuthorDTO> getAuthors(final Integer pageNumber, final Integer size);
    AuthorDTO getAuthorById(Integer id);
    AuthorDTO addAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthorById(AuthorDTO updatedAuthor);
    void deleteAuthorById(Integer id);
}
