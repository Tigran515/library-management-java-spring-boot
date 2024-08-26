package com.library.libraryspringboot.dto;

import com.library.libraryspringboot.dto.validation.PostValidation;
import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.entity.Author;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuthorDTO {
    @Null(message = "The ID field must be null in the POST request", groups = PostValidation.class)
    @NotNull(groups = PutValidation.class)
    private String id;
    @NotBlank(message = "Name is required",groups = {PostValidation.class, PutValidation.class})
    @Pattern(regexp = "^[^0-9]+$", message = "Name cannot contain numbers",groups =  {PostValidation.class, PutValidation.class})
    private String name;
    @Pattern(regexp = "^$|^[a-zA-Z]+$", message = "Lastname must only contain alphabetic characters",groups =  {PostValidation.class, PutValidation.class})
    private String lname;
    @Pattern(regexp = "^$|^[a-zA-Z]+$", message = "Surname must only contain alphabetic characters",groups =  {PostValidation.class, PutValidation.class})//@TODO: fix the BUG -> more than one name is not accepted
    private String sname;
    @Max(value = 2023, message = "Not valid number",groups =  {PostValidation.class, PutValidation.class}) //@TODO: change to constant current year
    private Integer born;

    public AuthorDTO() {

    }

    public AuthorDTO(Author author) {
        this.id = String.valueOf(author.getId());
        this.name = author.getName();
        this.lname = author.getLname();
        this.sname = author.getSname();
        this.born = author.getBorn();
    }

    @Override
    public String toString() {
        return String.format("[name=%s,lname=%s,sname=%s,born=%s]", name, lname, sname, born);
    }
}
