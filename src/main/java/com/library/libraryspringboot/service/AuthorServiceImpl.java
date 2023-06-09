package com.library.libraryspringboot.service;

import com.library.libraryspringboot.Tool.AuthorConverter;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import dto.AuthorDTO;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.NoSuchElementException;
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
    public Optional<AuthorDTO> getAuthorById(@NotNull Integer id) {
        Optional<Author> author = Optional.ofNullable(authorRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Author with ID " + id + " not found")));
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
    public AuthorDTO updateAuthorFields(AuthorDTO updatedAuthorDTO, @NotNull Integer id) {
        Author existingAuthor = authorRepository.findById(String.valueOf(id)).orElseThrow(() -> new NoSuchElementException("Author with ID " + id + " not found"));
        LOGGER.info("Author with [id=" + id + "] was found");
        BeanUtils.copyProperties(updatedAuthorDTO, existingAuthor, "id");
        Author author = authorRepository.save(existingAuthor);
        return authorConverter.fromEntityToDto(author);
    }

    @Override
    public void deleteAuthorById(@NotNull Integer id) {
        Optional<Author> author = authorRepository.findById(String.valueOf(id)); //@TODO: 2. delete related data to the author from bookAuthor also (use @Cascade)
        if (author.isEmpty()) {
            // Do not waste JVM resources on creation of 1 time usage strings / 'resources'
            //String errorMsg = "Author with [ID=" + id + "] does not exist";
            LOGGER.error(MessageFormat.format("Cannot delete author with non existing [ID={0}]", id));
            return;
        }
        authorRepository.deleteById(String.valueOf(id));
        LOGGER.info("Author with [id=" + id + "] was deleted");
    }

}
//@TODO: Research: do I need to add LOGGER.error in the .orElseThrow()
