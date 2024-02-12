package com.derrick.jwtauthenticationapp.config;

import com.derrick.jwtauthenticationapp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    static final String SECRET_KEY = "dccdea313132538213c5077a988c7d4627b6d2cfac9b1541bcd1eb3083b56a53";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
                .signWith( getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
