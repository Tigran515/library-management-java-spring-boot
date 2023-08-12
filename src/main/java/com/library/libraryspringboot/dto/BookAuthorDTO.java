package com.library.libraryspringboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO { //@TODO: is validation needed here ?
    @NotNull(message = "The bookDto in BookAuthorDTO is null")
    private BookDTO bookDTO;
    @NotNull(message = "The authorDTO in BookAuthorDTO is null")
    private AuthorDTO authorDTO;
}
