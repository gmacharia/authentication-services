package com.bookstore.authenticationservices.controllers;

import com.bookstore.authenticationservices.dtos.request.JWTAuthenticationRequest;
import com.bookstore.authenticationservices.dtos.response.JWTResponse;
import com.bookstore.authenticationservices.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private static final String GENERATE_JWT = "jwt";

    private final AuthenticationService authenticationService;

    @PostMapping(GENERATE_JWT)
    public Mono<JWTResponse> onGenerateJwtResponse(@RequestBody @Valid JWTAuthenticationRequest
                                                           jwtAuthenticationRequest) {
        log.info("About to authenticate the request {}", jwtAuthenticationRequest);
        return this.authenticationService.onGenerateJWT(jwtAuthenticationRequest);
    }
}
