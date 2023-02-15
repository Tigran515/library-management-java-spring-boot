package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
}
