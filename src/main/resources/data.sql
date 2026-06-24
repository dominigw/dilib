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