package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.BookConverter;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import dto.BookDTO;
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

    public BookServiceImpl(BookRepository bookRepository, BookConverter bookConverter, BookAuthorService bookAuthorService, AuthorRepository authorRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
        this.bookAuthorService = bookAuthorService;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public Page<BookDTO> getAllBooks(Integer pageNumber, Integer size) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(pageNumber, size));
        if (books.isEmpty()) {
            throw new NoSuchElementException("No book data is available");
        }
        return books.map(bookConverter::fromEntityToDto);
    }

    @Override
    public Optional<BookDTO> getBookById(Integer id) {
        Optional<Book> book = Optional.ofNullable(bookRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException(String.format("Book with ID %s not found", (id)))));
        return book.stream().map(bookConverter::fromEntityToDto).findFirst();
    }

    @Override
    @Transactional
    public BookAuthorDTO addBook(BookDTO bookDTO, Integer authorId) {
        Objects.requireNonNull(bookDTO, "BookDTO cannot be null");
        Objects.requireNonNull(authorId, "Author ID cannot be null");

        if (bookRepository.existsBookByTitleAndISBN(bookDTO.getTitle(), bookDTO.getISBN())) {
            throw new DuplicateKeyException("Book already exists");
        }
        Book book = bookConverter.fromDtoToEntity(bookDTO);
        book = bookRepository.save(book);
        Author author = authorRepository.findById(authorId.toString()) // author's Repository is used instead of Service
                .orElseThrow(() -> new NoSuchElementException(String.format("Author with ID %s not found", (authorId))));

        return bookAuthorService.addBookAuthor(book, author);
    }

    @Override
    @Transactional
    public void deleteBookById(Integer id) {
//        Optional<Book> book = Optional.ofNullable(bookRepository.findById(String.valueOf(id)).orElseThrow(() -> new ResourceNotFoundException("Book with ID not found")));
//        Optional<BookAuthor> bookAuthor = bookAuthorRepository.findBookAuthorByBookId(book.orElseThrow(() -> new ResourceNotFoundException("no bookAuthor found with this BookID")));
//        bookRepository.deleteById(String.valueOf(id));
//        bookAuthorRepository.deleteBookAuthorByBookId(book.orElseThrow(() -> new ResourceNotFoundException("Error while deleting the book")));
////        Optional<Book> book = bookRepository.findById(String.valueOf(id));
////        bookAuthorRepository.findBookAuthorByBookId(book.orElseThrow(() -> new ResourceNotFoundException("Book with ID not found "))); // ! what is checked here ? book is null or not

        Optional<Book> book = bookRepository.findById(String.valueOf(id));
        if (book.isEmpty()) {
            throw new NoSuchElementException("Book with ID not found in books");
        }

        //@TODO: !fix findBookAuthorByBookId (BUG with Optional)
        Optional<BookAuthor> bookAuthor = bookAuthorRepository.findBookAuthorByBookId(book.orElseThrow(() -> new NoSuchElementException("no such  book  exists in author")));
        if(bookAuthor.isEmpty()){ // if statement is temporary here, it will be removed after the BUG will be handled out
            throw new NoSuchElementException("Book with ID not found in book author");
        }
    }

}
