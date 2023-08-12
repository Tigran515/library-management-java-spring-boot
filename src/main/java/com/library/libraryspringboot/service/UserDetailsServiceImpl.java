package com.library.libraryspringboot.service;

import com.library.libraryspringboot.tool.UserDetailsImpl;
import com.library.libraryspringboot.entity.User;
import com.library.libraryspringboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(userName);
        user.orElseThrow(() -> {
            LOGGER.error("User with [username=" + userName + "] does not exist");
            return new UsernameNotFoundException("Not found: " + userName);
        });

        LOGGER.info("User with [username=" + userName + "] successfully logged in");
        return user.map(UserDetailsImpl::new).get();
    }
}
