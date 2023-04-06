package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.*;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import dto.BookAuthorDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
//    private final AuthorRepository authorRepository;

    //    private ModelMapper modelMapper;
//    private final AuthorConverter authorConverter;
//    private final BookConverter bookConverter;
    private final BookAuthorConverter bookAuthorConverter;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, AuthorRepository authorRepository, BookRepository bookRepository, AuthorConverter authorConverter, BookConverter bookConverter, BookAuthorConverter bookAuthorConverter) {
        this.bookAuthorRepository = bookAuthorRepository;
//        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
//        this.authorConverter = authorConverter;
//        this.bookConverter = bookConverter;
        this.bookAuthorConverter = bookAuthorConverter;
    }

    @Override
    public List<BookAuthorDTO> getAll() {
        List<BookAuthor> bookAuthors = (List<BookAuthor>) bookAuthorRepository.findAll();
        if (bookAuthors.isEmpty()) {
            throw new ResourceNotFoundException("no book and authors found");
        }
        return bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
    }

    @Override
    //1. change return type to <?> Wildcard
    public <T> T findBooksAndAuthorsByCriteria(SearchCriteria detail) {// @TODO 2.add Validation for detail

        if (detail.getISBN() != null) {
            Optional<Book> book = Optional.ofNullable(bookRepository.findBookByISBN(detail.getISBN()).orElseThrow(() -> new ResourceNotFoundException("no book found with this ID")));
            Optional<BookAuthor> bookAuthor = bookAuthorRepository.findBookAuthorByBookId(book.orElseThrow(() -> new ResourceNotFoundException("no bookAuthor found with this BookID")));
            return (T) bookAuthor.stream().map(bookAuthorConverter::fromEntityToDto).findFirst();
        }

        if (detail.getBookTitle() != null) {

            if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) { //Searches for Book and Author
                List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
                if (bookAuthors == null) {
                    throw new ResourceNotFoundException("book and author didn't match");
                }
                return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
            }

            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new ResourceNotFoundException("search book doesn't exist");
            }
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) {
            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new ResourceNotFoundException("No author found");
            }
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());

        }

        return null;
    }
}
