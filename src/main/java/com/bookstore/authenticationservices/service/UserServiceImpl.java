package com.bookstore.authenticationservices.service;


import com.bookstore.authenticationservices.entities.Users;
import com.bookstore.authenticationservices.exception.UserCredentialsIncorrectException;
import com.bookstore.authenticationservices.exception.UserNotFoundException;
import com.bookstore.authenticationservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<Users> onFindUserByUsername(String username) {
        return Mono.defer(() -> Mono.defer(() -> Mono.fromCallable(() ->
                        this.userRepository.findByUsername(username))
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(Schedulers.boundedElastic())));
    }

    @Override
    public Mono<Users> onAuthenticateUser(String username, String password) {
        log.info("We are authenticating the user provided {}", username);
        return this.onFindUserByUsername(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found Error")))
                .flatMap(users -> {
                    // Hash the password.
                    try {
                        var hashedPassword = this.hashingSHA256(password);
                        if (!hashedPassword.equalsIgnoreCase(users.getPassword())) {
                            return Mono.error(new UserCredentialsIncorrectException("Incorrect password provided"));
                        }
                    } catch (Exception e) {
                        log.info("An error ocurred while attempting to process password authentication");
                        return Mono.error(new Exception(e.getMessage()));
                    }
                    return Mono.just(users);
                });
    }
}
