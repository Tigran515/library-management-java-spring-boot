package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.*;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.entity.BookAuthor;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.repository.BookAuthorRepository;
import com.library.libraryspringboot.repository.BookRepository;
import dto.BookAuthorDTO;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    //    private ModelMapper modelMapper;
    private final AuthorConverter authorConverter;
    private final BookConverter bookConverter;
    private final BookAuthorConverter bookAuthorConverter;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, AuthorRepository authorRepository, BookRepository bookRepository, AuthorConverter authorConverter, BookConverter bookConverter, BookAuthorConverter bookAuthorConverter) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorConverter = authorConverter;
        this.bookConverter = bookConverter;
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
    public List<Object> findBooksAndAuthorsByCriteria(SearchCriteria detail) {// @TODO 1.add Validation 2. make return type Generics
        List<Author> authors = null;
        List<Book> books = null;

//if(detail.getISBN()!=null){ @TODO add
//    //search only ISBN and ignoring all other inputs
//    return findByISBN(ISBN)

//}
        if (detail.getISBN() != null) {
             books = bookRepository.findAll(new BookSpecification(detail));
        }

//        if(detail.getBookTitle()!=null){
//            // poisk proisxodid tolko v bookAuthor
//        }else{
//          return author
//        }

//        if (!StringUtils.isBlank(detail.getAuthorName()) || !StringUtils.isBlank(detail.getAuthorLname()) || !StringUtils.isBlank(detail.getAuthorSname())) {
//            authors = authorRepository.findAll(new AuthorSpecification(detail));
//        }
//        if (!StringUtils.isBlank(detail.getBookTitle())) {
//            books = bookRepository.findAll(new BookSpecification(detail));
//        }
//
//        if (authors != null && books == null) {
//            if (authors.isEmpty()) {
//                throw new ResourceNotFoundException("Author not found");
//            }
//            return authors.stream().map(authorConverter::fromEntityToDto).collect(Collectors.toList());
//
//        } else if (books != null && authors == null) {
//            if (books.isEmpty()) {
//                throw new ResourceNotFoundException("Book not found");
//            }
//            return books.stream().map(bookConverter::fromEntityToDto).collect(Collectors.toList());
//        } else if (authors != null && books != null) {  // the logic is that only one author and book can match
////            if (authors.size() > 0 && books.size() > 0) {
//            BookAuthorId bookAuthorId = new BookAuthorId(books.get(0), authors.get(0)); //fix the 🐛Bug
//            if (bookAuthorRepository.existsBookAuthorByBookAuthorId(bookAuthorId)) {
//                return Collections.singletonList(bookAuthorRepository.findById(bookAuthorId)); //@TODO change findById to BookAuthorDTO
//            } else {
//                throw new ResourceNotFoundException("Nothing found");
////                }
//            }
//        }
        return null;
    }

    @Override
    public List<BookAuthorDTO> getBookAuthorByBookId(Book id) {
        List<BookAuthor> bookAuthor = bookAuthorRepository.findBookAuthorByBookId(id);
        return bookAuthor.stream().map(bookAuthorConverter::fromEntityToDto).collect(Collectors.toList());
//        return bookAuthorRepository.findBookAuthorByBookId(id);
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
