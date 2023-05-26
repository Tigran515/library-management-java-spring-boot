package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.AuthorConverter;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import dto.AuthorDTO;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    @Override
    public Page<AuthorDTO> getAuthors(Integer offset, Integer limit) {
        Page<Author> authors = authorRepository.findAll(PageRequest.of(offset, limit));
        LOGGER.info("[count=" + authors.getSize() + "] authors were found");
        return authors.map(authorConverter::fromEntityToDto);
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(Integer id) {// @TODO: 1.manage the argument validation
        Optional<Author> author = Optional.ofNullable(authorRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new java.util.NoSuchElementException("Author with ID " + id + " not found")));
        LOGGER.info("Author with [id=" + id + "] was found");
        return author.stream().map(authorConverter::fromEntityToDto).findFirst();
    }

    @Override
    public AuthorDTO addAuthor(@Valid AuthorDTO authorDTO) {
        if (authorRepository.existsAuthorByNameAndLnameAndSname(authorDTO.getName(), authorDTO.getLname(), authorDTO.getSname())) {
            String errorMsg = "Author with " + authorDTO.toString() + " that you are trying to add already exists";
            LOGGER.error(errorMsg);
            throw new DuplicateKeyException(errorMsg);
        }
        Author saveAuthor = authorConverter.fromDtoToEntity(authorDTO);
        authorRepository.save(saveAuthor);
        LOGGER.info("Author was saved");
        return authorConverter.fromEntityToDto(saveAuthor);
    }

    @Override
    public void deleteAuthorById(Integer id) { //@TODO: 1.manage argument validation
        //@TODO: 2. delete data related to the author from book author also
        Optional<Author> author = authorRepository.findById(String.valueOf(id));
        if (author.isEmpty()) {
            // Do not waste JVM resources on creation of 1 time usage strings / 'resources'
            //String errorMsg = "Author with [ID=" + id + "] does not exist";
            LOGGER.error(MessageFormat.format("Cannot delete user with non existing id: {0}", id));
            return;
//            throw new NoSuchElementException(errorMsg);
        }
        authorRepository.deleteById(String.valueOf(id));
        LOGGER.info("Author with [id=" + id + "] was deleted");
    }

}
