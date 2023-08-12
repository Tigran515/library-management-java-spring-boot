package com.library.libraryspringboot.service;

import com.library.libraryspringboot.tool.*;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final BookAuthorConverter bookAuthorConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorServiceImpl.class);

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository,BookRepository bookRepository, AuthorConverter authorConverter, BookAuthorConverter bookAuthorConverter) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
        this.bookAuthorConverter = bookAuthorConverter;
    }

    @Override
    public Page<BookAuthorDTO> getBooksAndAuthors(Integer pageNumber, Integer size) {
        Page<BookAuthor> bookAuthors = bookAuthorRepository.findAll(PageRequest.of(pageNumber, size));
        LOGGER.info("[count=" + bookAuthors.getSize() + "] authors were found");
        return bookAuthors.map(bookAuthorConverter::fromEntityToDto);
    }

    @Override
    public BookAuthorDTO addBookAuthor(@Valid Book book, Author author) {
        BookAuthorId bookAuthorId = new BookAuthorId(book, author);

        if (bookAuthorRepository.existsBookAuthorByBookAuthorId(bookAuthorId)) {
            String errorMsg = "Book with "+book.toString()+"and author with "+author.toString()+" that you are trying to add already exists.";
            LOGGER.error(errorMsg);
            throw new DuplicateKeyException(errorMsg);
        }
        BookAuthor bookAuthor = new BookAuthor(bookAuthorId);
        bookAuthorRepository.save(bookAuthor);
        LOGGER.info("BookAuthor was saved");
        return bookAuthorConverter.fromEntityToDto(bookAuthor);
    }

    @Override
    //1. change return type to <?> Wildcard
    public <T> T findBooksAndAuthorsByCriteria(SearchCriteria detail) {// @TODO 2.add Validation for detail

        if (detail.getISBN() != null) { // @TODO: check if changes in BookAuthorRepository didn't effect on this method!!
            Book book = bookRepository.findBookByISBN(detail.getISBN()).orElseThrow(() -> new NoSuchElementException("Failed to find book. No book with this ISBN exists"));
            BookAuthor bookAuthor = bookAuthorRepository.findBookAuthorByBookId(book).orElseThrow(() -> new NoSuchElementException("Failed to find book and author."));
            LOGGER.info("Search result of book with [ISBN=" + detail.getISBN() + "] was found");

            return (T) bookAuthorConverter.fromEntityToDto(bookAuthor);
        }

        if (detail.getBookTitle() != null) {

            if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) { //Searches for Book and Author
                List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
                if (bookAuthors == null) {
                    String errorMsg = "Book with [title="+detail.getBookTitle()+"] and author[name="+detail.getAuthorName()+"lname="+detail.getAuthorLname()
                            +"sname="+detail.getAuthorSname() +"] does not exists.";  //manage this
                    LOGGER.error(errorMsg);
                    throw new NoSuchElementException(errorMsg);
                }
                LOGGER.info("Search result of book with matching author was found");
                return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
            }

            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new NoSuchElementException("Search book(s) doesn't exist");
            }
            LOGGER.info("Search result of book(s) [title=" + detail.getBookTitle() + "] was found");
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) {
            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new NoSuchElementException("No author found");
            }
            LOGGER.info("Search result of book by author's detail(s) was found");
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        if (detail.getDetail() != null) {
            System.out.println("det " + detail.getDetail());
            List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
            if (bookAuthors == null) {
                throw new NoSuchElementException("No author found");
            }
            LOGGER.info("Search result by keyword was found");
            return (T) bookAuthors.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
        }

        return null;
    }
}
