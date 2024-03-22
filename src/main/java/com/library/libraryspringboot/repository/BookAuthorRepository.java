package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.tool.BookAuthorId;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    List<BookAuthor> findAll(Specification<BookAuthor> specification);

    Page<BookAuthor> findAll(final Pageable pageable);

    // Spring Data JPA Query Method written using the JPA Query Language (JPQL).
    @Query("SELECT ba FROM BookAuthor ba JOIN FETCH ba.bookAuthorId.authorId a WHERE ba.bookAuthorId.bookId = ?1")
    Optional<BookAuthor> findBookAuthorByBookId(Book id); // @TODO change to DTO

    @Modifying
    //The @Modifying annotation is used to enhance the @Query annotation so that we can execute not only SELECT queries, but also INSERT, UPDATE, DELETE, and even DDL queries
    @Query("DELETE FROM BookAuthor ba WHERE ba.bookAuthorId.bookId = ?1")
    void deleteBookAuthorByBookId(Book id);

    Boolean existsBookAuthorByBookAuthorId(BookAuthorId bookAuthorId);

    @Query("SELECT ba FROM BookAuthor ba JOIN FETCH ba.bookAuthorId.authorId a WHERE ba.bookAuthorId.bookId = ?1")
    List<BookAuthor> findAllBookAuthorByBookId(Book id);

    @Query("SELECT ba.bookAuthorId.authorId.id FROM BookAuthor ba WHERE ba.bookAuthorId.bookId = ?1")
    List<Integer> findAuthorIdByBookId(Book id); //@

//    BookAuthorId findBookAuthorByBookAndAuthor(Book book, Author author);
}
