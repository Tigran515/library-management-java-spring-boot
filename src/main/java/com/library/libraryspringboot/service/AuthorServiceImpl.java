package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.AuthorConverter;
import com.library.libraryspringboot.controller.exceptions.DataDuplicationException;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import dto.AuthorDTO;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private ModelMapper modelMapper; // remove if not used
    private final AuthorConverter authorConverter;

    public AuthorServiceImpl(AuthorRepository authorRepository,AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = (List<Author>) authorRepository.findAll();
        if (authors.isEmpty()) {
            throw new ResourceNotFoundException("no Author data is available");
        }
        return authors.stream().map(authorConverter::fromEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(@NotBlank Integer id) {
        Optional<Author> author = authorRepository.findById(String.valueOf(id));
        if (author.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Author with ID %s not found", (id)));
        }
        return author.stream().map(authorConverter::fromEntityToDto).findFirst();
    }

    @Override
    public Author addAuthor(@Valid AuthorDTO authorDTO) {

        if (authorRepository.existsAuthorByNameAndLnameAndSname(authorDTO.getName(), authorDTO.getLname(), authorDTO.getSname())) {
            throw new DataDuplicationException("Author already exists");
        }
        Author saveAuthor = authorConverter.fromDtoToEntity(authorDTO); // @TODO: after end of the testing change return type to AuthorDTO and in Controller
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

//    @Override
//    public List<AuthorDTO> findAuthorsByDetails(String name, String lname, String sname) {  //TODO: look for @annotations
////        if ((name == null || name.isBlank()) && (lname == null || lname.isBlank()) && (sname == null || sname.isBlank())) {
////            //throw new MethodInvalidArgumentException("author's inputs are not valid");
////        return null;
////        }
//        List<Author> authors = authorRepository.findAll(new AuthorSpecification(name, lname, sname));
//        if (authors.isEmpty()) {
//            System.out.println("inform the user --> no resul");
//            throw new MethodInvalidArgumentException("searched author(s) does not exist");
//        }
//
//        return authors.stream().map(AuthorDTO::fromEntityToDTO).collect(Collectors.toList());
//    }

}
