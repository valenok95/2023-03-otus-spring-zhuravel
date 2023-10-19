package com.example.homework07.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String name;
    private Long authorId;
    private Long genreId;
}
