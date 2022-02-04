package com.targa.labs.dev.repository;

import com.targa.labs.dev.entity.Book;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BookRepository implements PanacheRepositoryBase<Book, Long> {
}
