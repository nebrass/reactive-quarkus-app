package com.targa.labs.dev.rest.dto;

import com.targa.labs.dev.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface BookMapper {
    BookDTO toBookDto(Book book);

    Book toBookEntity(BookDTO dto);

    List<BookDTO> toBookDtoList(List<Book> book);

    void updateBookEntityFromDto(BookDTO dto, @MappingTarget Book book);
}
