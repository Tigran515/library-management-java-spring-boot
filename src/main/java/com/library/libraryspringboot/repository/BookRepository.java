package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
    boolean existsBookByTitleAndAndISBN(String title, String isbn);
}
