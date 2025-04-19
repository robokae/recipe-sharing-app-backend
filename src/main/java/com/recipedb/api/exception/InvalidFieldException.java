package com.recipedb.api.exception;

import lombok.Getter;

@Getter
public class InvalidFieldException extends RuntimeException {

    private final String fieldName;

    public InvalidFieldException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
