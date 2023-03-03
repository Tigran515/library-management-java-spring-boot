package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.Tool.BookAuthorId;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.BookAuthor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, BookAuthorId> {
    //    @EntityGraph(attributePaths = {"book"})
//    Optional<CrossTable> findById(String id);
//    List<BookAuthor> findByCrossTableIdBookId(Integer id);
//    List<Author> findByBook_Id(Integer bookId);
//    List<BookAuthor> findByBook_Id(Integer bookId); // uncomment
}
