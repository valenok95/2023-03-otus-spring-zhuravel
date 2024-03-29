package com.example.homework05.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String name;
    private String authorName;
    private String genreName;
}
