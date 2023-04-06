package ru.otus.otusspringstudy.exception;

public class ReadResourceException extends RuntimeException {
    public ReadResourceException(String message) {
        super(message);
    }
}
