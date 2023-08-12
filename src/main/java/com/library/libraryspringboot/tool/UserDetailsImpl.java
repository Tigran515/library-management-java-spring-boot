package com.library.libraryspringboot.tool;

import com.library.libraryspringboot.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() { //@TODO: in production chang this hardcode to live tracking
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  //@TODO: in production chang this hardcode to live tracking
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //@TODO: in production chang this hardcode to live tracking
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
