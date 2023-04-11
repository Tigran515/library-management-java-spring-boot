package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.*;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import dto.BookAuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final BookAuthorConverter bookAuthorConverter;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, AuthorRepository authorRepository, BookRepository bookRepository, AuthorConverter authorConverter, BookConverter bookConverter, BookAuthorConverter bookAuthorConverter) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
        this.bookAuthorConverter = bookAuthorConverter;
    }

    @Override
    public Page<BookAuthorDTO> getAllBooksAndAuthors(Integer pageNumber, Integer size) {
        Page<BookAuthor> bookAuthors = bookAuthorRepository.findAll(PageRequest.of(pageNumber, size));
        if (bookAuthors.isEmpty()) {
            throw new NoSuchElementException("no book and authors found");
        }
        return bookAuthors.map(bookAuthorConverter::fromEntityToDto);
    }

    @Override
    public BookAuthor addBookAuthor(Book book, Author author) { //@TODO DTO
        BookAuthorId bookAuthorId = new BookAuthorId(book, author);
        BookAuthor bookAuthor = new BookAuthor(bookAuthorId);
        return bookAuthorRepository.save(bookAuthor);
    }

    @Override
    //1. change return type to <?> Wildcard
    public <T> T findBooksAndAuthorsByCriteria(SearchCriteria detail) {// @TODO 2.add Validation for detail

        if (detail.getISBN() != null) {
            Optional<Book> book = Optional.ofNullable(bookRepository.findBookByISBN(detail.getISBN()).orElseThrow(() -> new NoSuchElementException("no book found with this ID")));
            Optional<BookAuthor> bookAuthor = bookAuthorRepository.findBookAuthorByBookId(book.orElseThrow(() -> new NoSuchElementException("no bookAuthor found with this BookID")));//@TODO: this method has a BUG (Optional)
            return (T) bookAuthor.stream().map(bookAuthorConverter::fromEntityToDto).findFirst();
        }

        if (detail.getBookTitle() != null) {

            if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) { //Searches for Book and Author
                List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
                if (bookAuthors == null) {
                    throw new NoSuchElementException("book and author didn't match");
                }
                return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
            }

            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new NoSuchElementException("search book doesn't exist");
            }
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) {
            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new NoSuchElementException("No author found");
            }
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        if (detail.getDetail() != null) {
            System.out.println("det " + detail.getDetail());
            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new NoSuchElementException("No author found");
            }
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        return null;
    }
}
