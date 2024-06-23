package com.bookstore.authenticationservices.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }

}
