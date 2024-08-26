package com.library.libraryspringboot.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserFieldsUpdateRequest {
//    @NotNull(message = "User ID is null!")  // commented that the server could throw 400
    private Integer id;
    @Pattern(regexp = "^[^0-9]+$", message = "Name cannot contain numbers")
    private String name;
    @Pattern(regexp = "^[^0-9]+$", message = "Last name cannot contain numbers")
    private String lname;
    @Pattern(regexp = "^[^0-9]+$", message = "Surname cannot contain numbers")
    private String sname;

    public UserFieldsUpdateRequest() {
    }

}
