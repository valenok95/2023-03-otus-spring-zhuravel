package com.example.homework08.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String name;
    private String authorName;
    private String genreName;
}
