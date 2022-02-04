package com.targa.labs.dev.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Size(min = 13, max = 20)
    private String isbn;
    private String author;
    @DecimalMin("0.0")
    private BigDecimal price;

    public Book(String title, String isbn, String author, BigDecimal price) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.price = price;
    }
}
