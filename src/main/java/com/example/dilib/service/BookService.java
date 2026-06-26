package com.example.dilib.service;

import com.example.dilib.exception.ResourceNotFoundException;
import com.example.dilib.model.Author;
import com.example.dilib.model.Book;
import com.example.dilib.repository.AuthorRepository;
import com.example.dilib.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book saveBookWithAuthors(Book book, Set<Long> authorIds) {
        if (authorIds != null && !authorIds.isEmpty()) {

            List<Author> foundAuthors = authorRepository.findAllById(authorIds);

            book.setAuthors(new HashSet<>(foundAuthors));
        }
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> searchBooksByAuthor(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }
}
