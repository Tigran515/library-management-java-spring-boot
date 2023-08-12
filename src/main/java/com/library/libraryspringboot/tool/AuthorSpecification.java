package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.entity.Author;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

//public class AuthorSpecification implements Specification<Author> { // searches in all fields and returns first match
//    private final String search;
//
//    public AuthorSpecification(String search) {
//        this.search = search;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//        String likePattern = "%" + search + "%";
//        return builder.or(
//                builder.like(builder.lower(root.get("name")), likePattern),
//                builder.like(builder.lower(root.get("lname")), likePattern),
//                builder.like(builder.lower(root.get("sname")), likePattern)
//        );
//    }
//
//}
public class AuthorSpecification implements Specification<Author> { //@TODO: delete the class if no usage
    private final String name;
    private final String lname;
    private final String sname;
    //private final SearchCriteria detail add getter/setter

    public AuthorSpecification(String name, String lname, String sname) {
        this.name = name;
        this.lname = lname;
        this.sname = sname;
    }

    public AuthorSpecification(SearchCriteria detail){ // may be a solution
        this.name = detail.getAuthorName();
        this.lname = detail.getAuthorLname();
        this.sname = detail.getAuthorSname();
    }

    @Override
    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            String nameLikePattern = "%" + name.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("name")), nameLikePattern));
        }

        if (lname != null && !lname.isEmpty()) {
            String lastNameLikePattern = "%" + lname.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("lname")), lastNameLikePattern));
        }

        if (sname != null && !sname.isEmpty()) {
            String surnameLikePattern = "%" + sname.toLowerCase() + "%";
            predicates.add(builder.like(builder.lower(root.get("sname")), surnameLikePattern));
        }
// *note -- //
        Predicate authorPredicate = builder.and(predicates.toArray(new Predicate[0]));
        if (authorPredicate == null) {
            return null;
        } else {
            return authorPredicate;
        }

//        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
// *note -- //        Predicate authorPredicate = builder.and(predicates.toArray(new Predicate[0])); return authorPredicate;
