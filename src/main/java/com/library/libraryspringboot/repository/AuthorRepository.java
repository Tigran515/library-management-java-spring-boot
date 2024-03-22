package com.library.libraryspringboot.repository;

import com.library.libraryspringboot.entity.Author;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, String> {
    @NotNull Page<Author> findAll(final @NotNull Pageable pageable);
    boolean existsAuthorByNameAndLnameAndSname(String name, String lname, String sname);
}


//Example of update method
//
//    @Modifying
//    @Query("UPDATE YourEntity e SET e = :updatedEntity WHERE e.id = :id")
//    void updateFieldsIgnoreId(@Param("id") Long id, @Param("updatedEntity") YourEntity updatedEntity);
