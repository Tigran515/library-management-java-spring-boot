package com.library.libraryspringboot.entity;

import com.library.libraryspringboot.enums.RoleEnum;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 320, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 180, nullable = false)
    private String name;

    @Column(length = 180, nullable = false)
    private String lname;

    @Column(length = 180, nullable = false)
    private String sname;

    @Enumerated(EnumType.STRING)//by default Ordinal 01
    @Column(length = 10, nullable = false, columnDefinition = "ENUM('admin', 'user') DEFAULT 'user'")
    private RoleEnum role;

    @Column(name = "created_at",nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created;

    @Column(name = "last_login")
    private Timestamp lastConnection; //change to lastConnection + column

    @Column(length = 5, nullable = false)
    private Boolean active;


    public Integer id() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public RoleEnum getRole() {
        return this.role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getLastConnection() {
        return lastConnection;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
