package com.bookstore.authenticationservices.service;


import com.bookstore.authenticationservices.configs.ApplicationProperties;
import com.bookstore.authenticationservices.dtos.request.JWTAuthenticationRequest;
import com.bookstore.authenticationservices.dtos.response.JWTResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    @Override
    public Mono<JWTResponse> onGenerateJWT(JWTAuthenticationRequest jwtAuthenticationRequest) {
        return this.userService.onAuthenticateUser(jwtAuthenticationRequest.getUsername()
                        , jwtAuthenticationRequest.getPassword())
                .flatMap(users -> Mono.just(this.onConstructJWTToken(applicationProperties, users)))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
