package com.example.homework05.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private final long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
