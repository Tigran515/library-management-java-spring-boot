package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.Author;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, String> { //TODO: change to Integer
    List<Author> findAll(Specification<Author> specification);

    boolean existsAuthorByNameAndLnameAndSname(String name, String lname, String sname);

    //    boolean existsAuthorByNameOrLnameOrSnameOrLname(String name, String lname, String sname);
//    boolean existsAuthorByNameAndOrLnameAndOrSname(String name, String lname, String sname);
}