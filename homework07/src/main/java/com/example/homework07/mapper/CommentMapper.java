package com.example.homework07.mapper;

import com.example.homework07.dto.CommentDto;
import com.example.homework07.dto.CommentResponseDto;
import com.example.homework07.model.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private final ModelMapper modelMapper;

    public CommentMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Comment.class, CommentResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getBook().getName(),
                    CommentResponseDto::setBookName);
            mapper.map(src -> src.getBook().getAuthor().getName(),
                    CommentResponseDto::setAuthorName);
        });
    }

    public Comment toModel(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

    public CommentResponseDto toResponseDto(Comment comment) {
        return modelMapper.map(comment, CommentResponseDto.class);
    }

}
