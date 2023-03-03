package com.library.libraryspringboot.Tool;

import com.library.libraryspringboot.entity.Author;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecification implements Specification<Author> { //@TODO: read about Specification<T>
    private String search;

    public AuthorSpecification(String search) {
        this.search = search;
    }

    @Override
    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String likePattern = "%" + search + "%";
        return builder.or(
                builder.like(builder.lower(root.get("name")), likePattern),
                builder.like(builder.lower(root.get("lname")), likePattern)
        );
    }

}
