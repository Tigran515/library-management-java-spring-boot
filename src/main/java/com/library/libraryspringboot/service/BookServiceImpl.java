package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.tool.BookConverter;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import com.library.libraryspringboot.dto.BookDTO;
import com.library.libraryspringboot.tool.ValidationTool;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final BookAuthorService bookAuthorService;
    private final BookAuthorRepository bookAuthorRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final ValidationTool validationTool;

    public BookServiceImpl(BookRepository bookRepository, BookConverter bookConverter, BookAuthorService bookAuthorService,
                           BookAuthorRepository bookAuthorRepository, ValidationTool validationTool) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
        this.bookAuthorService = bookAuthorService;
        this.bookAuthorRepository = bookAuthorRepository;
        this.validationTool = validationTool;
    }

    @Override
    public Page<BookDTO> getBooks(Integer offset, Integer limit) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(offset, limit));
        LOGGER.info("[count=" + books.getSize() + "] authors were found");
        return books.map(bookConverter::fromEntityToDto);
    }

    @Override
    public BookDTO getBookById(@NotNull Integer id) {
        Book book = bookRepository.findById(String.valueOf(id))
                .orElseThrow(() -> { // @TODO: in this case it's okay to throw an exception but //check findById exception type
                    String errorMsg = "Book with [ID=" + id + "] was not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        LOGGER.info("Book with [id=" + id + "] was found");
        return bookConverter.fromEntityToDto(book);
    }

    @Override
    @Transactional //@TODO:🔥 Angular!! also make changes in the Client code
    public List<BookAuthorDTO> addBook(BookDTO bookDTO) {
//        if (bookDTO == null) { if the author id is empty anyway an ILLEGAL ARG is thrwon
//            String errorMsg = "Cannot add Book because it is null.";
//            LOGGER.error(errorMsg);
//            throw new IllegalArgumentException(errorMsg);
//        }
        validationTool.validateByGroup(bookDTO, Default.class);

        if (bookDTO.getAuthorId().isEmpty()) {
            String errorMsg = "Author ID list is empty.";
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        if (bookRepository.existsBookByISBN(bookDTO.getISBN())) {
            String errorMsg = "Book with [ISBN=" + bookDTO.getISBN() + "] already exists.";
            LOGGER.error(errorMsg);
            throw new DuplicateKeyException(errorMsg);
        }

        Book book = bookConverter.fromDtoToEntity(bookDTO);
        bookRepository.save(book);
        bookDTO.setId(String.valueOf(book.getId()));
        List<BookAuthorDTO> bookAuthorDTO = bookAuthorService.addBookAuthor(bookDTO);
        LOGGER.info("Book with [id=" + book.getId() + "] was added");

        return bookAuthorDTO;
    }

    @Override
    @Transactional
    @SuppressWarnings("ConstantConditions")
    public List<BookAuthorDTO> updateBookById(BookDTO bookDTO) {
        if (bookDTO.getId() == null) { // Despite the warning, the null check is required.//The Service layer trusts only itself in case of validation.// Anyway,the validation tool validates the id.
            String errorMsg = "Book with [id=null] not found";
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        validationTool.validateByGroup(bookDTO, PutValidation.class);
        Book existingBook = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> {
                    String errorMsg = "Book with [ID=" + bookDTO.getId() + "] not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        BeanUtils.copyProperties(bookDTO, existingBook);
        bookRepository.save(existingBook);
        LOGGER.info(MessageFormat.format("Book with [id={0}] was updated", existingBook.getId()));

        List<Integer> existingAuthorIds = bookAuthorRepository.findAuthorIdByBookId(existingBook);
        if (existingAuthorIds == null) {
            LOGGER.warn("Book with [id=" + bookDTO.getId() + "] not found");
        } else {
            List<Integer> authorsToDelete = existingAuthorIds.stream()
                    .filter(id -> !bookDTO.getAuthorId().contains(id)).toList();
            List<Integer> authorsToAdd = bookDTO.getAuthorId().stream()
                    .filter(id -> !existingAuthorIds.contains(id)).toList();

            if (!authorsToDelete.isEmpty()) {
                BookDTO updateBookDTO = new BookDTO(existingBook, authorsToDelete);
                bookAuthorService.deleteBookAuthorById(updateBookDTO);
//                    LOGGER.info("Book with [id=" + existingBook.getId() + "] and Author with [id=" + author.getId() + "] was deleted from BookAuthor"); //@FIXME: another service already has LOGGER.info
            }
            if (!authorsToAdd.isEmpty()) {
                BookDTO updatedBookDTO = new BookDTO(existingBook, authorsToAdd);
                bookAuthorService.addBookAuthor(updatedBookDTO);
//                LOGGER.info("Book with [id=" + existingBook.getId() + "] and Author with [id=" + author.getId() + "] was added to BookAuthor"); //@FIXME: another service already has LOGGER.info
            }
        }
        return bookAuthorService.getBookAuthorById(existingBook);
    }


    @Override
    @Transactional
    public void deleteBookById(@NotNull Integer id) { //@TODO:IMPORTANT!// 1.refactor the code to  RESTful API convention by not returning any value
        Optional<Book> book = bookRepository.findById(String.valueOf(id));
        if (book.isEmpty()) {
            LOGGER.error("Book with [ID=" + id + "] does not exists");
            return;
        }
        List<BookAuthor> bookAuthorList = bookAuthorRepository.findAllBookAuthorByBookId(book.get());
        if (bookAuthorList.isEmpty()) {
            LOGGER.error("Book with [ID= " + id + "] was not found");
//            return;
        }
        bookRepository.deleteById(String.valueOf(id));
        bookAuthorRepository.deleteBookAuthorByBookId(book.get());
        LOGGER.info("Book with [ID=" + id + "] was removed");
    }
}
