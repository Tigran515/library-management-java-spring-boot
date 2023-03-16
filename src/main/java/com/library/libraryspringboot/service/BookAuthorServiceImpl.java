package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.AuthorSpecification;
import com.library.libraryspringboot.Tool.BookAuthorId;
import com.library.libraryspringboot.Tool.BookrSpecification;
import com.library.libraryspringboot.Tool.SearchCriteria;
import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import dto.AuthorDTO;
import dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookAuthor> getAll() {
        return (List<BookAuthor>) bookAuthorRepository.findAll();
    }

    @Override
    public List<Object> findBooksAndAuthorsByCriteria(SearchCriteria detail) {// @TODO 1.change List into Optional  2. update the Generics
        List<Author> authors = null; // change to Optional
        List<Book> books = null; // change to Optional
        if (detail.isEmpty()) {
            throw new MethodInvalidArgumentException("search inputs are empty| service");
        }

        if (detail.hasAuthorCriteria()) { //@TODO: 1. add validation if doesn't contain whitespaces 2. author exists or not
            authors = authorRepository.findAll(new AuthorSpecification(detail.getAuthorName(), detail.getAuthorLname(), detail.getAuthorSname()));
        }
        if (detail.getBookTitle() != null || detail.getISBN() != null) { //@TODO: 1. add validation if doesn't contain whitespaces 2. book exists or not
            books = bookRepository.findAll(new BookrSpecification(detail.getBookTitle(), detail.getISBN()));
        }

        if (authors != null && books == null) {
            if (authors.isEmpty()) {
                throw new ResourceNotFoundException("Author not found");
            }
            return authors.stream().map(AuthorDTO::fromEntityToDTO).collect(Collectors.toList());

        } else if (books != null && authors == null) {
            if (books.isEmpty()) {
                throw new ResourceNotFoundException("Book not found");
            }
            return books.stream().map(BookDTO::fromEntityToDTO).collect(Collectors.toList());

        } else if (authors != null && books != null) { // the logic is that only one author and book can match
            BookAuthorId bookAuthorId = new BookAuthorId(books.get(0), authors.get(0));
            if (bookAuthorRepository.existsBookAuthorByBookAuthorId(bookAuthorId)) {
                return Collections.singletonList(bookAuthorRepository.findById(bookAuthorId)); //@TODO change findById to BookAuthorDTO
            } else {
                throw new MethodInvalidArgumentException("Nothing found");
            }
        }
        return null;
    }

//    @Override
//    public List<BookAuthor> getAuthorByBookId(Integer id) {  // uncomment
//        return bookAuthorRepository.findByBook_Id(id);
//    }

//    @Override
//    public List<Author> getAuthorByBookId(Integer bookId) {
//        return bookAuthorRepository.findByBook_Id(bookId);
//    }

//    @Override
//    public Optional<CrossTable> getById(Integer id) {
//        return crossTableRepository.findById(id);
//    }

//    @Override
//    public Iterable<Author> getAuthorsByBookId(Integer bookId) {
//        return bookRepository.findById(String.valueOf(bookId)).orElseThrow(() -> new ResourceNotFoundException("Book not found"))
//                .getAuthors();
//    }

}
