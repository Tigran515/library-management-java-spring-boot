package com.library.libraryspringboot.service;

import com.library.libraryspringboot.dto.validation.PostValidation;
import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.tool.AuthorConverter;
import com.library.libraryspringboot.entity.Author;
import com.library.libraryspringboot.repository.AuthorRepository;
import com.library.libraryspringboot.dto.AuthorDTO;
import com.library.libraryspringboot.tool.ValidationTool;
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
    private final ValidationTool validationTool;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorConverter authorConverter, ValidationTool validationTool) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
        this.validationTool = validationTool;
    }

    @Override
    public Page<AuthorDTO> getAuthors(Integer offset, Integer limit) {
        Page<Author> authors = authorRepository.findAll(PageRequest.of(offset, limit));
        LOGGER.info("[count=" + authors.getSize() + "] authors were found");
        return authors.map(authorConverter::fromEntityToDto);
    }

    @Override
    public AuthorDTO getAuthorById(@NotNull Integer id) {
        Author author = authorRepository.findById(String.valueOf(id))
                .orElseThrow(() -> {
                    String errorMsg = "Author with [ID=" + id + "] was not found";
                    LOGGER.error(errorMsg);
                    return new NoSuchElementException(errorMsg);
                });
        LOGGER.info("Author with [id=" + id + "] was found");
        return authorConverter.fromEntityToDto(author);
    }

    @Override
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            String errorMsg = "Cannot add Author because it is null.";
            LOGGER.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        validationTool.validateByGroup(authorDTO, PostValidation.class);
        if (authorRepository.existsAuthorByNameAndLnameAndSname(authorDTO.getName(), authorDTO.getLname(), authorDTO.getSname())) {
            String errorMsg = "Author with " + authorDTO + " that you are trying to add already exists";
            LOGGER.error(errorMsg);
            throw new DuplicateKeyException(errorMsg);
        }
        Author saveAuthor = authorConverter.fromDtoToEntity(authorDTO);
        authorRepository.save(saveAuthor);
        LOGGER.info("Author was saved");
        return authorConverter.fromEntityToDto(saveAuthor);
    }

    @Override //@TODO:🔥 Angular!! also make changes in the Client code
    public AuthorDTO updateAuthorById(AuthorDTO updatedAuthorDTO) { //@NOTE: REFACTORED! get the id from AuthorDTO
//        if (updatedAuthorDTO.getId() == null) { //NOTE: the same logic is done in validation tool
//            String errorMsg = "Cannot update the Author. The [ID=null]";
//            LOGGER.error(errorMsg);
//            throw new IllegalArgumentException(errorMsg);
//        }
        validationTool.validateByGroup(updatedAuthorDTO, PutValidation.class);
        Author existingAuthor = authorRepository.findById(updatedAuthorDTO.getId()).orElse(null);//@TODO: change to orElseThrow
        if (existingAuthor == null) {
            String errorMsg = "Author with [ID=" + updatedAuthorDTO.getId() + "] not found";
            LOGGER.error(errorMsg);
            throw new NoSuchElementException(errorMsg);
        }
        BeanUtils.copyProperties(updatedAuthorDTO, existingAuthor, "id"); //throws an HTTP 400
        Author author = authorRepository.save(existingAuthor);
        LOGGER.info(MessageFormat.format("Author with [id={0}] was updated", author.getId()));
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
