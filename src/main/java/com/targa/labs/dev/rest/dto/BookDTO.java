package com.targa.labs.dev.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private String author;
    private BigDecimal price;
}
