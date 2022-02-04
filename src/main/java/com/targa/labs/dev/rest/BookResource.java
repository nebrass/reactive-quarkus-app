package com.targa.labs.dev.rest;

import com.targa.labs.dev.rest.dto.BookDTO;
import com.targa.labs.dev.service.BookService;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/books")
@RequestScoped
public class BookResource {

    private final BookService bookService;

    @GET
    public Uni<Response> findAll() {
        return bookService.findAll()
                .map(data -> ok(data).build());
    }

    @GET
    @Path("/{id}")
    public Uni<Response> findById(@PathParam("id") Long id) {
        return bookService.findById(id)
                .onItem().ifNotNull().transform(book -> ok(book).build())
                .onItem().ifNull().continueWith(status(NOT_FOUND)::build);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(BookDTO bookDTO) {
        return bookService.save(bookDTO)
                .onItem().ifNotNull().transform(book -> ok(book).build())
                .onItem().ifNull().continueWith(status(BAD_REQUEST)::build);
    }

    @GET
    @Path("/author/{name}")
    public Uni<Response> findByAuthor(@PathParam("name") String name) {
        return bookService.findByAuthor(name)
                .onItem().ifNotNull().transform(book -> ok(book).build())
                .onItem().ifNull().continueWith(status(NOT_FOUND)::build);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteById(@PathParam("id") Long id) {
        return bookService.deleteById(id);
    }
}
