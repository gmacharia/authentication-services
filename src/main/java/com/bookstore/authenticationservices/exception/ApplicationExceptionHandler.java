package com.bookstore.authenticationservices.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.bookstore.authenticationservices.dtos.error.ErrorResponse;
import com.bookstore.authenticationservices.dtos.error.FieldError;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Order(-2)
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Slf4j
public class ApplicationExceptionHandler implements ErrorWebExceptionHandler {
    @NonNull
    @Override
    public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {
        final ServerHttpResponse response = exchange.getResponse();

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof WebExchangeBindException webExchangeBindException) {

            response.setStatusCode(HttpStatus.BAD_REQUEST);
            List<FieldError> fieldErrorsList = webExchangeBindException.getAllErrors().stream()
                    .map(objectError -> FieldError.builder()
                            .missingFieldName(Arrays.toString(Objects.requireNonNull(objectError.getCodes())))
                            .missingFieldErrorMessage(objectError.getDefaultMessage())
                            .build()).toList();

            var errorResponse = ErrorResponse.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .validationErrors(fieldErrorsList).build();
            final Mono<DataBuffer> buffer = buildDataBuffer(
                    exchange,
                    errorResponse
            );
            log.error("BadRequestException " + ex.getMessage());
            return response.writeWith(buffer);
        }
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

        if (ex instanceof UserNotFoundException || ex instanceof UserCredentialsIncorrectException){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        }
        final Mono<DataBuffer> buffer = buildDataBuffer(
                exchange,
                ErrorResponse.builder()
                        .errorCode(ex.getMessage()).build()
        );
        log.error("BadRequestException " + ex.getMessage());
        return response.writeWith(buffer);

    }

    private Mono<DataBuffer> buildDataBuffer(@org.springframework.lang.NonNull ServerWebExchange exchange, ErrorResponse errorResponse) {
        return Mono.fromCallable(() -> new ObjectMapper().writeValueAsBytes(errorResponse))
                .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes));
    }
}
