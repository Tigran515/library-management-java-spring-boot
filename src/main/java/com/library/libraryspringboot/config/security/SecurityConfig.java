package com.library.libraryspringboot.config.security;

import com.library.libraryspringboot.config.security.jwt.JwtRequestFilter;
import com.library.libraryspringboot.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // @TODO: remove prePostEnabledcl
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll()
                .requestMatchers(HttpMethod.GET, "/authors/**", "/books/**", "/users/**", "/bookAuthor/**").permitAll() //@TODO:DELETE this line
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // @FIXME:after testing set access to admin only .hasRole()
                .requestMatchers(HttpMethod.PUT, "/users/**", "/authors/**", "/books/**", "/bookAuthor/**", "/users/**").permitAll()//@TODO:DELETE this line
                .requestMatchers(HttpMethod.DELETE, "/users/**", "/authors/**", "/books/**").permitAll()  //@TODO:DELETE this line
                .requestMatchers(HttpMethod.PATCH, "/users/**").permitAll()  //@TODO:DELETE this line
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider());

        // .exceptionHandling((exceptions) -> exceptions
        //  .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
        //   .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // makes sure that this filter (jwtRequestFilter) is called before UsernamePasswordAuthenticationFilter is called

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
