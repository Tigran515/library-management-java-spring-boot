package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.Tool.BookAuthorId;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import dto.BookAuthorDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, BookAuthorId> {
    //    @EntityGraph(attributePaths = {"book"})
//    Optional<CrossTable> findById(String id);
//    List<BookAuthor> findByCrossTableIdBookId(Integer id);
//    List<Author> findByBook_Id(Integer bookId);
//    List<BookAuthor> findByBook_Id(Integer bookId); // uncomment
//    BookAuthor searchBookAuthorByBookAuthorId(String bookId, String authorId);
//    boolean existsBookAuthorByBookIdAndAuthorId(String bookId, String authorId);

    List<BookAuthor> findAll(Specification<BookAuthor> specification);

    //        @Query("SELECT ba FROM BookAuthor ba WHERE ba.bookAuthorId.bookId = ?1")
    @Query("SELECT ba FROM BookAuthor ba JOIN FETCH ba.bookAuthorId.authorId a WHERE ba.bookAuthorId.bookId = ?1")
    Optional<BookAuthor> findBookAuthorByBookId(Book id);

    @Query("SELECT ba FROM BookAuthor ba JOIN FETCH ba.bookAuthorId.authorId a WHERE ba.bookAuthorId.bookId = ?1")
    List<BookAuthor> findAllBookAuthorByBookId(Book id);
}
