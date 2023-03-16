package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {
    List<Book> findAll(Specification<Book> specification);
    boolean existsBookByTitleAndISBN(String title, String isbn);

//    boolean existsBookByTitleOrISBN(String title, String ISBN);
}
