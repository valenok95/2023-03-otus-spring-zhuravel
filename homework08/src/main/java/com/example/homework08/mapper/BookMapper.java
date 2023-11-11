package com.example.homework08.mapper;

import com.example.homework08.dto.BookDto;
import com.example.homework08.dto.BookResponseDto;
import com.example.homework08.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    private final ModelMapper modelMapper;

    public BookMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Book.class, BookResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getAuthor().getName(),
                    BookResponseDto::setAuthorName);
            mapper.map(src -> src.getGenre().getName(),
                    BookResponseDto::setGenreName);
        });
    }

    public Book toModel(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    public BookResponseDto toResponseDto(Book book) {
        return modelMapper.map(book, BookResponseDto.class);
    }

}
