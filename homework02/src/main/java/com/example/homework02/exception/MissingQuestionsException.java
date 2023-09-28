package com.example.homework02.exception;

public class MissingQuestionsException extends RuntimeException {

    public MissingQuestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
