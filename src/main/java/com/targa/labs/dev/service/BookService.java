package com.targa.labs.dev.service;

import com.targa.labs.dev.rest.dto.BookMapper;
import com.targa.labs.dev.repository.BookRepository;
import com.targa.labs.dev.rest.dto.BookDTO;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Uni<List<BookDTO>> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookDto)
                .collect()
                .asList();
    }

    public Uni<BookDTO> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookDto);
    }

    public Uni<BookDTO> save(BookDTO book) {
        return bookRepository.persistAndFlush(bookMapper.toBookEntity(book))
                .map(bookMapper::toBookDto);
    }

    public Uni<List<BookDTO>> findByAuthor(String author) {
        return bookRepository.find("lower(author) like lower(CONCAT('%', '" + author + "', '%')) ")
                .stream()
                .map(bookMapper::toBookDto)
                .collect()
                .asList();
    }

    @ReactiveTransactional
    public Uni<Void> deleteById(Long id) {
        return bookRepository.deleteById(id).replaceWithVoid();
    }
}
