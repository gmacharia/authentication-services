package com.bookstore.authenticationservices.dtos.response;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseArray implements Serializable {
    private String statusCode;
    private String statusDescription;
    private List<Object> data;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
