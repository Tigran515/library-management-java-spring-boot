package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification implements Specification<Book> {   //@TODO: delete the class if no usage
    private final String title;
    private final String isbn;

    public BookSpecification(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public BookSpecification(SearchCriteria detail) {
        this.title = detail.getBookTitle();
        this.isbn = detail.getIsbn();
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (isbn != null && !isbn.isEmpty()) {
            String lastNameLikePattern = "%" + isbn.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("ISBN")), lastNameLikePattern));
            return builder.and(predicates.toArray(new Predicate[0])); // alternative for findByISBN(String ISBN)
        }

        if (title != null && !title.isEmpty()) {
            String nameLikePattern = "%" + title.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("title")), nameLikePattern));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
// *note -- //        Predicate authorPredicate = builder.and(predicates.toArray(new Predicate[0])); return authorPredicate;
