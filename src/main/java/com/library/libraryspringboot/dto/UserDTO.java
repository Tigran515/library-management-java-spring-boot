package com.library.libraryspringboot.dto;

import com.library.libraryspringboot.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String name;
    private String lname;
    private String sname;
    private String role;

    public UserDTO(){}

    public UserDTO(User user){
        this.username = user.getUsername();
        this.name= user.getName();
        this.lname = user.getLname();
        this.sname = user.getSname();
        this.role = String.valueOf(user.getRole());
    }
}
