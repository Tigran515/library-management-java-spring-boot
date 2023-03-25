package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.BookConverter;
import com.library.libraryspringboot.controller.exceptions.DataDuplicationException;
import com.library.libraryspringboot.controller.exceptions.ResourceNotFoundException;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private ModelMapper modelMapper;
    private final BookConverter bookConverter;

    public BookServiceImpl(BookRepository bookRepository, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    @Override
    public List<dto.BookDTO> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        if (books.isEmpty()){
            throw new ResourceNotFoundException("no Book data is available");
        }
        return books.stream().map(bookConverter::fromEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<dto.BookDTO> getBookById(Integer id) {
        Optional<Book> book = bookRepository.findById(String.valueOf(id));
        if (book.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Author with ID %s not found", (id)));
        }
        return book.stream().map(bookConverter::fromEntityToDto).findFirst();
    }

    @Override
    public Book addBook(dto.BookDTO bookDTO) {
        if (bookRepository.existsBookByTitleAndISBN(bookDTO.getTitle(), bookDTO.getISBN())) {
            throw new DataDuplicationException("Book already exists");
        }
        Book saveBook = bookConverter.fromDtoToEntity(bookDTO);
//        System.out.println(saveBook.getId());
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

//    @Override
//    public List<BookDTO> findBooksByDetails(String title, String ISBN) {
////        if ((title == null || title.isBlank()) && (ISBN == null || ISBN.isBlank())) {
////            throw new MethodInvalidArgumentException("book's inputs are not valid");
////        }
//        List<Book> books = bookRepository.findAll(new BookrSpecification(title, ISBN));
//        if (books.isEmpty()) {
//            throw new MethodInvalidArgumentException("searched book(s) does not exist");
//        }
//        return books.stream().map(BookDTO::fromEntityToDTO).collect(Collectors.toList());
//    }
}
