package com.auth.userAuthentication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    // TODO: Create a method that will generate a token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 + 60 + 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); // This "compact" method refreshes the token

    }

    // TODO: Create a boolean method that can validate a token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // TODO: Create a method "isTokenExpired"
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }



    // TODO: Create a method "extractExpiration"
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    // TODO: Create a method that can extract single claims being passed
    // TODO: Hint: Use a generic method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // TODO: Extract all the claims from the "token" being passed as a parameter
        final Claims claims = extractAllClaims(token);
        // TODO: Return the "claimsResolver" the function being passed as parameter
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
