package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.AuthorConverter;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import dto.AuthorDTO;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    @Override
    public Page<AuthorDTO> getAllAuthors(Integer pageNumber, Integer size) {
        Page<Author> authors = authorRepository.findAll(PageRequest.of(pageNumber, size));
        return authors.map(authorConverter::fromEntityToDto);
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(@NotBlank Integer id) {// @TODO: add log.info() here
        Optional<Author> author = Optional.ofNullable(authorRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new java.util.NoSuchElementException(String.format("Author with ID %s not found", (id)))));
        //log.info("Author with [id="+id+"] was found");
        return author.stream().map(authorConverter::fromEntityToDto).findFirst();
    }

    @Override
    public Author addAuthor(@Valid AuthorDTO authorDTO) {
        if (authorRepository.existsAuthorByNameAndLnameAndSname(authorDTO.getName(), authorDTO.getLname(), authorDTO.getSname())) {
            throw new DuplicateKeyException("Author already exists");
        }
        Author saveAuthor = authorConverter.fromDtoToEntity(authorDTO); // @TODO: after end of the testing change return type to AuthorDTO and in Controller

        return authorRepository.save(saveAuthor);
    }

    @Override
    public void deleteAuthorById(Integer id) {
        Optional<Author> author = authorRepository.findById(String.valueOf(id));
        if (author.isEmpty()) {
            throw new NoSuchElementException(String.format("Author with ID %s not found", (id)));
        }
        authorRepository.deleteById(String.valueOf(id));
    }

}
