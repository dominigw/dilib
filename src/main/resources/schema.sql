DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE authors
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    biography VARCHAR(1000)
);

CREATE TABLE books
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn             VARCHAR(50)  NOT NULL UNIQUE,
    title            VARCHAR(255) NOT NULL,
    genre            VARCHAR(100),
    available_copies INT          NOT NULL
);

CREATE TABLE book_author
(
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

CREATE TABLE rentals
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    book_id     BIGINT      NOT NULL,
    rental_date DATE        NOT NULL,
    due_date    DATE        NOT NULL,
    return_date DATE,
    status      VARCHAR(50) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books (id)
);