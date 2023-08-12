package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface AuthorService {
    Page<AuthorDTO> getAuthors(final Integer pageNumber, final Integer size);
    Optional<AuthorDTO> getAuthorById(Integer id);
    AuthorDTO addAuthor(AuthorDTO authorDTO);
    void deleteAuthorById(Integer id);
    AuthorDTO updateAuthorFields(AuthorDTO updatedAuthor,Integer id);
}
