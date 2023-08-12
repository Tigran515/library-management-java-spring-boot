package com.library.libraryspringboot.service;

import com.library.libraryspringboot.tool.SearchCriteria;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.entity.Book;
import com.library.libraryspringboot.dto.BookAuthorDTO;
import org.springframework.data.domain.Page;


public interface BookAuthorService {
    <T> T findBooksAndAuthorsByCriteria(SearchCriteria detail); // advanced search method
    Page<BookAuthorDTO> getBooksAndAuthors(final Integer pageNumber, final Integer size);
    BookAuthorDTO addBookAuthor(Book book, Author author);
}
