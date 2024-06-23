package com.bookstore.authenticationservices.exception;

public class UserCredentialsIncorrectException extends Exception{

    public UserCredentialsIncorrectException(String exceptionMessage){
        super(exceptionMessage);
    }

}
