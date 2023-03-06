package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.AuthorSpecification;
import com.library.libraryspringboot.controller.exceptions.DataDuplicationException;
import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import dto.AuthorDTO;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        return authors.stream().map(AuthorDTO::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(@NotBlank Integer id) {
        Optional<Author> author = authorRepository.findById(String.valueOf(id));
        if (author.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Author with ID %s not found", (id)));
        }
        return author.stream().map(AuthorDTO::fromEntityToDTO).findFirst();
    }

    @Override
    public Author addAuthor(@Valid AuthorDTO author) {

        if (authorRepository.existsAuthorByNameAndLnameAndSname(author.getName(), author.getLname(), author.getSname())) {
            System.out.println("texekacnel client-in");
            throw new DataDuplicationException("Author already exists");
        }
        Author saveAuthor = AuthorDTO.fromDTOToEntity(author);
        return authorRepository.save(saveAuthor);
    }

    @Override
    public void deleteAuthorById(Integer id) {
        Optional<Author> author = authorRepository.findById(String.valueOf(id));
        if (author.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Author with ID %s not found", (id)));
        }
        authorRepository.deleteById(String.valueOf(id));
    }

    @Override
    public List<AuthorDTO> findAuthorsByDetails(String detail) {  //TODO: look for @annotations
        if (detail == null || detail.isBlank()) {
            throw new MethodInvalidArgumentException("searched author's ID is null/blank");
        }
        List<Author> authors = authorRepository.findAll(new AuthorSpecification(detail));
        if (authors.isEmpty()) {
            System.out.println("inform the user --> no resul");
        }
        return authors.stream().map(AuthorDTO::fromEntityToDTO).collect(Collectors.toList());
    }

}
