package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookrSpecification implements Specification<Book> {
    private final String title;
    private final String ISBN;

    public BookrSpecification(String title, String ISBN) {
        this.title = title;
        this.ISBN = ISBN;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (title != null && !title.isEmpty()) {
            String nameLikePattern = "%" + title.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("title")), nameLikePattern));
        }

        if (ISBN != null && !ISBN.isEmpty()) {
            String lastNameLikePattern = "%" + ISBN.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("ISBN")), lastNameLikePattern));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
// *note -- //        Predicate authorPredicate = builder.and(predicates.toArray(new Predicate[0])); return authorPredicate;