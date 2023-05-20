package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Page<Book> findAll(final Pageable pageable);
    boolean existsBookByISBN(String isbn);
    Optional<Book> findBookByISBN(String isbn);
}
