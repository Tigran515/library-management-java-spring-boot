package com.library.libraryspringboot.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { //next important,
        // pulls information from current Token
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) { // JWT tokens are decoded here
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) { //Most important method, creates JWT based on userDetails
        Map<String, Object> claims = new HashMap<>(); //Currently empty,but you can pass any other specific claims that you want to include in the payload
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())) //builder pattern is used here
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 sec= 1000 * 10
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }//NOTE:in .setSubject the subject is the person who is/has being authenticated/successfully authenticated in this case

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
