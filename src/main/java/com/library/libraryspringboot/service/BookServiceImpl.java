package com.library.libraryspringboot.service;

import com.library.libraryspringboot.controller.exceptions.DataDuplicationException;
import com.library.libraryspringboot.controller.exceptions.MethodInvalidArgumentException;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.repository.BookRepository;
import dto.BookDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        return books.stream().map(BookDTO::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getBookById(Integer id) {
        Optional<Book> book = bookRepository.findById(String.valueOf(id));
        if (book.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Author with ID %s not found", (id)));
        }
        return book.stream().map(BookDTO::fromEntityToDTO).findFirst();
    }

    @Override
    public Book addBook(BookDTO book) {
        if (bookRepository.existsBookByTitleAndAndISBN(book.getTitle(), book.getISBN())) {
            System.out.println("texekacnel client-in");
            throw new DataDuplicationException("Book already exists");
        }
        Book saveBook = BookDTO.fromDTOToEntity(book);
        System.out.println(saveBook.getId());
        return bookRepository.save(saveBook);
    }

    @Override
    public void deleteBookById(Integer id) {
        Optional<Book> book = bookRepository.findById(String.valueOf(id));
        if (book.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Book with ID %s not found", (id)));
        }
        bookRepository.deleteById(String.valueOf(id));
    }
}
