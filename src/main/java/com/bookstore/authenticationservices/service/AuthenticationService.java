package com.bookstore.authenticationservices.service;

import com.bookstore.authenticationservices.configs.ApplicationProperties;
import com.bookstore.authenticationservices.dtos.request.JWTAuthenticationRequest;
import com.bookstore.authenticationservices.dtos.response.JWTResponse;
import com.bookstore.authenticationservices.entities.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;

public interface AuthenticationService {
    Mono<JWTResponse> onGenerateJWT(JWTAuthenticationRequest jwtAuthenticationRequest);

    default JWTResponse onConstructJWTToken(ApplicationProperties applicationProperties, Users users) {
        Claims claims = Jwts.claims();
        claims.setIssuedAt(new Date());
        claims.setSubject(users.getUsername());
        claims.put("userId", users.getUserId());
        claims.put("email", users.getUsername());
        claims.put("groups", users.getUserGroups());

        // set token expiration period
        long expMillis = System.currentTimeMillis() + applicationProperties.getJwtTtl();
        Date exp = new Date(expMillis);
        claims.setExpiration(exp);

        Key key = Keys.hmacShaKeyFor(applicationProperties.getJwtSharedSecret().getBytes());
        var jwtString = Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                //.compressWith(CompressionCodecs.GZIP)
                .compact();
        return JWTResponse.builder()
                .token(jwtString)
                .statusCode("200")
                .build();
    }
}
