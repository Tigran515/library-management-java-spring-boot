package com.library.libraryspringboot.dto;

import com.library.libraryspringboot.entity.Author;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class AuthorDTO  {
    @Null(message = "The ID field must be null in the POST request")
    private String id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[^0-9]+$", message = "Name cannot contain numbers")
    private String name;
    @Pattern(regexp = "^$|^[a-zA-Z]+$", message = "Lastname must only contain alphabetic characters")
    private String lname;
    @Pattern(regexp = "^$|^[a-zA-Z]+$", message = "Surname must only contain alphabetic characters")//@TODO: fix the BUG -> more than one name is not accepted
    private String sname;
    @Max(value = 2023,message = "Not valid number")
    private Integer born;

    public AuthorDTO(){

    }
    public AuthorDTO(Author author) {
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
