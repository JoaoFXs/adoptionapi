package io.gituhub.jfelixy.petadoptionapi.application.jwt;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String message) {
        super(message);
    }
}
