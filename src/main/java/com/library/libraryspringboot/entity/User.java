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
    private String username;
    private String password;
    private String name;
    private String lname;
    private String sname;
    @Enumerated(EnumType.STRING)//by default Ordinal 01
    private RoleEnum role;
    @Column(name = "created_at")
    private Timestamp created;
    @Column(name = "last_connection")
    private Timestamp lastConnection; //change to lastConnection + column
    private Boolean active;

    public User() {
    }

    public Integer getId() {
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

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Timestamp lastConnection) {
        this.lastConnection = lastConnection;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
