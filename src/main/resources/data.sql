INSERT INTO authors (name, biography)
VALUES ('Funny De Man', 'Writes funny stuff.');
INSERT INTO authors (name, biography)
VALUES ('Le Joseph Romance', 'Fan favourite.');

INSERT INTO books (isbn, title, genre, available_copies)
VALUES ('2746915780156', '1001 jokes i guess', 'Comedy', 5);
INSERT INTO books (isbn, title, genre, available_copies)
VALUES ('2453691572049', 'The Farm', 'Romance', 3);


INSERT INTO book_author (book_id, author_id)
VALUES (1, 1);
INSERT INTO book_author (book_id, author_id)
VALUES (2, 2);

INSERT INTO users (username, password, role) VALUES ('normal', '$2a$10$X59uNiA6W6mQ8pGTh0gNLeZ9b6W9M6R6Kx8A7R7w5v7z8Yx8m6m6.', 'USER');
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$X59uNiA6W6mQ8pGTh0gNLeZ9b6W9M6R6Kx8A7R7w5v7z8Yx8m6m6.', 'ADMIN');