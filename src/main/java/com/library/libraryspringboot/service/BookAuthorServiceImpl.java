package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.BookDTO;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.tool.*;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final BookAuthorConverter bookAuthorConverter;
    private final AuthorRepository authorRepository;
    private final ValidationTool validationTool;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookAuthorServiceImpl.class);

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, BookRepository bookRepository, BookAuthorConverter bookAuthorConverter,
                                 AuthorRepository authorRepository, ValidationTool validationTool) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
        this.bookAuthorConverter = bookAuthorConverter;
        this.authorRepository = authorRepository;
        this.validationTool = validationTool;
    }

    @Override
    public Page<BookAuthorDTO> getBooksAndAuthors(Integer pageNumber, Integer size) {
        Page<BookAuthor> bookAuthors = bookAuthorRepository.findAll(PageRequest.of(pageNumber, size));
        LOGGER.info("[count=" + bookAuthors.getSize() + "] authors were found");
        return bookAuthors.map(bookAuthorConverter::fromEntityToDto);
    }

    @Override
    public List<BookAuthorDTO> getBookAuthorById(Book bookId) {
        if (!bookRepository.existsById(String.valueOf(bookId.getId()))) { //@TODO: what it returns ?
            String errorMsg = "Book with[ID=" + bookId.getId() + "] was not found";
            LOGGER.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }

        List<BookAuthor> bookAuthors = bookAuthorRepository.findAllBookAuthorByBookId(bookId);
        if (bookAuthors.isEmpty()) {
            String errorMsg = "Book with[ID=" + bookId + "] has no author assigned";
            LOGGER.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
        return bookAuthors.stream()
                .map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
    }

    @Override
    //1. change return type to <?> Wildcard
    public <T> T findBooksAndAuthorsByCriteria(SearchCriteria detail) {// @TODO 2.add Validation for detail & handel the warnings ,if needed remove the exceptions

        if (detail.getIsbn() != null) { // @TODO: check if changes in BookAuthorRepository didn't effect on this method!!
            Book book = bookRepository.findBookByISBN(detail.getIsbn()).orElseThrow(() -> new NoSuchElementException("Failed to find book. No book with this ISBN exists"));
            BookAuthor bookAuthor = bookAuthorRepository.findBookAuthorByBookId(book).orElseThrow(() -> new NoSuchElementException("Failed to find book and author."));
            LOGGER.info("Search result of book with [ISBN=" + detail.getIsbn() + "] was found");

            return (T) bookAuthorConverter.fromEntityToDto(bookAuthor);
        }

        if (detail.getBookTitle() != null) {

            if (detail.getAuthorName() != null || detail.getAuthorLname() != null || detail.getAuthorSname() != null) { //Searches for Book and Author
                List<BookAuthor> bookAuthors = bookAuthorRepository.findAll(new BookAuthorSpecification(detail));
                if (bookAuthors == null) {
                    String errorMsg = "Book with [title=" + detail.getBookTitle() + "] and author[name=" + detail.getAuthorName() + "last name=" + detail.getAuthorLname()
                            + "surname=" + detail.getAuthorSname() + "] does not exists.";  //manage this
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

    //    @Override
//    public List<BookAuthorDTO> addBookAuthor(BookDTO bookDTO) { // @TODO: add validation tool
//        if (bookDTO == null) { //✅
//            String errorMsg = "Failed to perform the operation add book with author. Either the book is null or the author doesn't exist or is null.";
//            LOGGER.error(errorMsg);
//            throw new IllegalArgumentException(errorMsg);
//        }
//        if (bookDTO.getAuthorId() == null) {//✅
//            LOGGER.warn("Author ID is incorrect"); //✅ it's NOT a illegal argument exception
//            return null;
//        }
//        Book book = bookRepository.findById(bookDTO.getId())
//                .orElseThrow(() -> {
//                    String errorMsg = "Book with [id=" + bookDTO.getId() + "] not found";
//                    LOGGER.error(errorMsg);
//                    return new NoSuchElementException(errorMsg);
//                });
//        for (int id : bookDTO.getAuthorId()) { //@FIXME: avoid logic duplication, make it simple and gentle code
//            Author author = authorRepository.findById(String.valueOf(id)).orElse(null);
//            if (author == null) { //✅
//                LOGGER.warn("Author with [id=" + id + "] was not found");// ✅здесь не надо падать с ошикой
//                return null; //🐛BUG:#1 Doesn't add the next author in the list,if the previous was not found.NOTE(solution,use "continue") ✅
//                //🐛BUG:#2 if author is null,anyway book is added(depends on the use case,if the author can be added later,it's ok ✅
//            }
//            BookAuthorId bookAuthorId = new BookAuthorId(book, author);
//            if (bookAuthorRepository.existsBookAuthorByBookAuthorId(bookAuthorId)) {//✅
//                LOGGER.warn("Cannot add BookAuthor. Book with [id=" + book.getId() + "] and Author with [id=" + id + "] already exists");
////              return already existing bookAuthor; // ✅❗❗❗❗
//            } else { //@NOTE: use "continue" instead of "else condition"
//                BookAuthor bookAuthor = new BookAuthor(bookAuthorId);
//                bookAuthorRepository.save(bookAuthor);
//                LOGGER.info("Book with [id=" + book.getId() + "] and Author with [id=" + id + "] was saved to BookAuthor");
//            }
//        }
//        return bookAuthorRepository.findAllBookAuthorByBookId(book).stream().map(bookAuthorConverter::fromEntityToDto).
//                collect(Collectors.toList());
//    }
    @Override
    public List<BookAuthorDTO> addBookAuthor(BookDTO bookDTO) { // @TODO: add validation tool
        if (bookDTO == null) { //✅
            String errorMsg = "Failed to perform the operation add book with author. Either the book is null or the author doesn't exist or is null.";
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (bookDTO.getAuthorId() == null) {//✅
            LOGGER.warn("Author ID is incorrect"); //✅ it's NOT a illegal argument exception
            return null;
        }
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> {
                    String errorMsg = "Book with [id=" + bookDTO.getId() + "] not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        for (int id : bookDTO.getAuthorId()) { //@FIXME: avoid logic duplication, make it simple and gentle code
            Author author = authorRepository.findById(String.valueOf(id)).orElse(null);
            if (author == null) { //✅
                LOGGER.warn("Author with [id=" + id + "] was not found");// ✅здесь не надо падать с ошикой
//                return null; //🐛BUG:#1 Doesn't add the next author in the list,if the previous was not found.NOTE(solution,use "continue") ✅
                //🐛BUG:#2 if author is null,anyway book is added(depends on the use case,if the author can be added later,it's ok ✅
                continue;
            }
            BookAuthorId bookAuthorId = new BookAuthorId(book, author);
            if (bookAuthorRepository.existsBookAuthorByBookAuthorId(bookAuthorId)) {//✅
                LOGGER.warn("Cannot add BookAuthor. Book with [id=" + book.getId() + "] and Author with [id=" + id + "] already exists");
//              return already existing bookAuthor; // ✅❗❗❗❗ can be used for Authors List case
                continue;
            }
            //@NOTE: use "continue" instead of "else condition"
            BookAuthor bookAuthor = new BookAuthor(bookAuthorId);
            bookAuthorRepository.save(bookAuthor);
            LOGGER.info("Book with [id=" + book.getId() + "] and Author with [id=" + id + "] was saved to BookAuthor");
        }

        return bookAuthorRepository.findAllBookAuthorByBookId(book).stream().map(bookAuthorConverter::fromEntityToDto).
                collect(Collectors.toList());
    }

    @Override
    public void deleteBookAuthorById(BookDTO bookDTO) { //@FIXME: Ask the TL for approval , anyway it works correct.
        if (bookDTO == null) {
            LOGGER.error("Cannot delete non existing Book");
            return;
        }
        if (bookDTO.getAuthorId() == null) {
            LOGGER.error("Cannot delete. Illegal arguments, Author ID is null.");
            return;
        }
        validationTool.validate(bookDTO);
        Optional<Book> book = bookRepository.findById(bookDTO.getId());
        if (book.isEmpty()) {
            LOGGER.error("Book with [id=" + bookDTO.getId() + "] not found");
            return;
        }
        for (int id : bookDTO.getAuthorId()) {
            Optional<Author> author = authorRepository.findById(String.valueOf(id));
            if (author.isEmpty()) {
                LOGGER.warn("Author with [id=" + id + "] was not found");// ✅здесь не надо падать с ошикой
                continue;
            }
            BookAuthorId bookAuthorId = new BookAuthorId(book.get(), author.get());
            if (!bookAuthorRepository.existsBookAuthorByBookAuthorId(bookAuthorId)) {
                LOGGER.error(MessageFormat.format("Cannot delete, no BookAuthor found with Book [ID={0}] Author [ID={1}]", book.get().getId(), author.get().getId()));
                continue;
            }
            bookAuthorRepository.deleteById(bookAuthorId);
            LOGGER.info("Book with [id=" + book.get().getId() + "] and Author with [id=" + author.get().getId() + "] deleted from BookAuthor");
        }

    }
}
