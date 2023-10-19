package com.example.homework06.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String text;
    private String bookName;
    private String authorName;
}
