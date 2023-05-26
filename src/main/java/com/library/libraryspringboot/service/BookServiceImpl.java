package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.BookConverter;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import dto.BookAuthorDTO;
import dto.BookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final BookAuthorService bookAuthorService;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository, BookConverter bookConverter, BookAuthorService bookAuthorService, AuthorRepository authorRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
        this.bookAuthorService = bookAuthorService;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public Page<BookDTO> getBooks(Integer offset, Integer limit) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(offset, limit));
        LOGGER.info("[count=" + books.getSize() + "] authors were found");
        return books.map(bookConverter::fromEntityToDto);
    }

    @Override
    public Optional<BookDTO> getBookById(Integer id) {
        Optional<Book> book = Optional.ofNullable(bookRepository.findById(String.valueOf(id))
                .orElseThrow(() -> {
                    String errorMsg = "Book with [ID=" + id + "] was not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                }));
        LOGGER.info("Book with [id=" + id + "] was found");
        return book.stream().map(bookConverter::fromEntityToDto).findFirst();
    }

    @Override
    @Transactional
    public BookAuthorDTO addBook(BookDTO bookDTO, Integer authorId) {
        Objects.requireNonNull(bookDTO, "BookDTO cannot be null"); // @TODO: remove if not useful
        Objects.requireNonNull(authorId, "Author ID cannot be null"); // @TODO: remove if not useful

        if (bookRepository.existsBookByISBN(bookDTO.getISBN())) {
            String errorMsg = "Book with [ISBN=" + bookDTO.getISBN()+ "] that you are trying to add already exists.";//for the future authorId will be replaced with author
            LOGGER.error(errorMsg);
            throw new DuplicateKeyException(errorMsg);
        }
        Book book = bookConverter.fromDtoToEntity(bookDTO);
        bookRepository.save(book);
        Author author = authorRepository.findById(authorId.toString())
                .orElseThrow(() -> {
                    String errorMsg = "Author with [ID=" + authorId + "] not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        BookAuthorDTO bookAuthorDTO = bookAuthorService.addBookAuthor(book, author);
        LOGGER.info("Book with [id=" + book.getId() + "] was added");

        return bookAuthorDTO;
    }

    @Override
    @Transactional
    public void deleteBookById(Integer id) { //@TODO: IMPORTANT! 1.refactor the code to  RESTful convention by not returning any value 2.add argument validation
        Book book = bookRepository.findById(String.valueOf(id))
                .orElseThrow(() ->
                {
                    String errorMsg = "Book with [ID=" + id + "] does not exists";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        bookAuthorRepository.findBookAuthorByBookId(book)
                .orElseThrow(() ->
                {
                    String errorMsg = "Book with [ID= " + id + "] was not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });

        bookRepository.deleteById(String.valueOf(id));
        bookAuthorRepository.deleteBookAuthorByBookId(book);
        LOGGER.info("Book with [ID=" + id + "] was removed");
    }

}
