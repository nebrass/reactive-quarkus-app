package com.targa.labs.dev.repository;

import com.targa.labs.dev.entity.Book;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRepository implements PanacheRepositoryBase<Book, Long> {
}
