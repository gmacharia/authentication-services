package com.bookstore.authenticationservices.dtos.request;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTAuthenticationRequest implements Serializable {
    @NotNull(message = "parameter username is required")
    private String username;
    @NotNull(message = "parameter password is required")
    private String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
