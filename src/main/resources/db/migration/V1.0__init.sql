create sequence hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE books
(
    id     BIGINT NOT NULL PRIMARY KEY
        DEFAULT nextval('hibernate_sequence'),
    title  VARCHAR(100),
    isbn   VARCHAR(20)
        CONSTRAINT isbn_size_between_13_20 CHECK (char_length(isbn) >= 13),
    author VARCHAR(100),
    price  NUMERIC(6, 2)
        CONSTRAINT positive_price CHECK (price >= 0)
);

INSERT INTO books (title, isbn, author, price)
values ('Pairing Apache Shiro and Java EE 7', '978-1-365-12404-4', 'Nebrass Lamouchi', 0);
INSERT INTO books (title, isbn, author, price)
values ('Playing with Java Microservices on Kubernetes and OpenShift', '9782956428510', 'Nebrass Lamouchi', 9.18);
