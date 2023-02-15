package com.library.libraryspringboot.service;

import com.library.libraryspringboot.model.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> get() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthorByName(String name) {
        return authorRepository.findAuthorByName(name);
    }

    @Override
    public void deleteAuthorByName(String name) {
        List<Author> author = authorRepository.findAuthorByName(name);//@TODO: 1.add findAuthorByNameLname
        authorRepository.deleteAll(author); // 2.change to delete(author)
    }

    @Override
    public void deleteAuthor(String name, String lname) {
        Author author = authorRepository.findAuthorByNameAndLname(name, lname);
        authorRepository.delete(author);
    }
}
