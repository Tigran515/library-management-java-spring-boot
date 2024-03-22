package com.library.libraryspringboot.dto;

import com.library.libraryspringboot.dto.validation.PostValidation;
import com.library.libraryspringboot.dto.validation.PutValidation;
import com.library.libraryspringboot.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
    //    @Null(message = "The ID field must be null in the POST request")
//    private Integer id;
//    @NotBlank(message = "Username is required")
//    private String username;
//    @NotBlank(message = "Name is required")
//    private String name;
//    @NotBlank(message = "Last name is required")
//    private String lname;
//    @NotBlank(message = "Surname is required")
//    private String sname;
//    private RoleEnum role;
//    private Boolean active;
//    private Timestamp created;

    @NotNull(message = "The ID field must not be null in the PUT request", groups = PutValidation.class)
    private Integer id;
    @Null(message = "The username field must be null in the PUT request", groups = PutValidation.class)
    @NotBlank(message = "Username is required", groups = PostValidation.class)
    private String username;
    @NotBlank(message = "Name is required", groups = {PutValidation.class})
    @Pattern(regexp = "^[^0-9]+$", message = "Name cannot contain numbers")
    private String name;
    @NotBlank(message = "Last name is required", groups = {PutValidation.class})
    @Pattern(regexp = "^[^0-9]+$", message = "Last name cannot contain numbers", groups = {PutValidation.class})
    private String lname;
    @NotBlank(message = "Surname is required", groups = {PutValidation.class})
    @Pattern(regexp = "^[^0-9]+$", message = "Surname cannot contain numbers", groups = {PutValidation.class})
    private String sname;

    @Null(groups = PutValidation.class)
    private RoleEnum role;
    @Null(groups = PutValidation.class)
    private Boolean active;
    @Null(groups = PutValidation.class)
    private Timestamp created;

    public UserDTO() {
    }

    public UserDTO(String username, String name, String lname, String sname, RoleEnum role, Boolean active, Timestamp created) {
        this.username = username;
        this.name = name;
        this.lname = lname;
        this.sname = sname;
        this.role = role;
        this.active = active;
        this.created = created;
    }
//    public UserDTO(User user) {
//        this.username = user.getUsername();
//        this.name = user.getName();
//        this.lname = user.getLname();
//        this.sname = user.getSname();
//        this.role = user.getRole();
//    }

    @Override
    public String toString() {
        return String.format("[username=%s,lname=%s,sname=%s,born=%s]", username);
    }
}
