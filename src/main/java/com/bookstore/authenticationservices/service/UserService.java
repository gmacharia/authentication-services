package com.bookstore.authenticationservices.service;

import com.bookstore.authenticationservices.entities.Users;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    Mono<Users> onFindUserByUsername(String username);

    Mono<Users> onAuthenticateUser(String username, String password);

    default String hashingSHA256(String plainTextForHashing) throws Exception {
        String hashedPin;
        MessageDigest md;
        byte[] bytes;
        try {
            // Static getInstance method is called with hashing SHA
            md = MessageDigest.getInstance("SHA-256");

            /* digest() method called
             * to calculate message digest of an input
             * and return array of byte
             */
            bytes = md.digest(plainTextForHashing.getBytes(StandardCharsets.UTF_8));

            // bytes to hex
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            hashedPin = sb.toString();

        } catch (NoSuchAlgorithmException ex) {

            throw new Exception("Text Hashing Failed: " + ex.getMessage());
        }
        return hashedPin;
    }
}
