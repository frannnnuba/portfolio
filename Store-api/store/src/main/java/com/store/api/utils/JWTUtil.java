package com.store.api.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    @Value("${spring.security.jwt.secret_key}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration}")
    private Long expiration; 

    public String generateToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).
        expiration(new Date(System.currentTimeMillis()+ expiration)).
        signWith(getSignInKey(),Jwts.SIG.HS256).compact();
    }

    public String extractUsername(String token){
        Claims body= getClaims(token);
        return body.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public Boolean validateToken(String username, UserDetails userDetails,String token){
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Boolean isTokenExpired(String token){
        Claims body = getClaims(token);
        return body.getExpiration().before(new Date());
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}