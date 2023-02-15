package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.model.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, String> { //TODO: change to Integer
    List<Author> findAuthorByName(String name);//findAuthorByDetails
    Author findAuthorByNameAndLname(String name, String lname);

}