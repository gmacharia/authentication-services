package com.bookstore.authenticationservices.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FieldError implements Serializable {
    private String missingFieldName;
    private String missingFieldErrorMessage;
}
