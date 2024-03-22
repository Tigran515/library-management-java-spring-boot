package com.library.libraryspringboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Last name is required")
    private String lname;
    @NotBlank(message = "Surname is required")
    private String sname;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should contain at least 6 characters")
    private String password;

    @Override
    public String toString() {
        return String.format("[username=%s]", username);
    }
}
