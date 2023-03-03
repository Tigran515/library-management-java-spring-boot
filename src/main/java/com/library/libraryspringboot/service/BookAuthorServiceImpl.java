package com.library.libraryspringboot.service;

import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private BookRepository bookRepository;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    @Override
    public List<BookAuthor> getAll() {
        return (List<BookAuthor>) bookAuthorRepository.findAll();
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
