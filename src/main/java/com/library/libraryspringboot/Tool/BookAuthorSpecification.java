package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookAuthorSpecification implements Specification<BookAuthor> {
private SearchCriteria detail;
//    private final String title;
//    private final String name;
//    private final String lname;
//    private final String sname;

    public BookAuthorSpecification(SearchCriteria detail) {
        this.detail = detail;
    }


    @Override
    public Predicate toPredicate(Root<BookAuthor> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        Join<BookAuthor, Book> bookJoin = root.join("bookAuthorId").join("bookId");
        Join<BookAuthor, Author> authorJoin = root.join("bookAuthorId").join("authorId");

        if (detail.getBookTitle() != null) {
            predicates.add(builder.like(builder.lower(bookJoin.get("title")), "%" + detail.getBookTitle().toLowerCase() + "%"));
        }

        if (detail.getAuthorName() != null) {
            predicates.add(builder.like(builder.lower(authorJoin.get("name")), "%" + detail.getAuthorName().toLowerCase() + "%"));
        }

        if (detail.getAuthorLname() != null) {
            predicates.add(builder.like(builder.lower(authorJoin.get("lname")), "%" + detail.getAuthorLname().toLowerCase() + "%"));
        }

        if (detail.getAuthorSname() != null) {
            predicates.add(builder.like(builder.lower(authorJoin.get("sname")), "%" + detail.getAuthorSname().toLowerCase() + "%"));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
