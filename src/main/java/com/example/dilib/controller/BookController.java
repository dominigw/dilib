package com.example.dilib.controller;

import com.example.dilib.dto.AuthorResponseDTO;
import com.example.dilib.dto.BookRequestDTO;
import com.example.dilib.dto.BookResponseDTO;
import com.example.dilib.model.Book;
import com.example.dilib.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getBooks(@RequestParam(required = false) String author) {
        List<Book> books = new ArrayList<>();

        if (author != null && !author.isBlank()) {
            books = bookService.searchBooksByAuthor(author);
        }
        else {
            books = bookService.getAllBooks();
        }

        List<BookResponseDTO> dtoList = books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        return ResponseEntity.ok(convertToDto(book));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setIsbn(bookRequestDTO.isbn());
        book.setTitle(bookRequestDTO.title());
        book.setGenre(bookRequestDTO.genre());
        book.setAvailableCopies(bookRequestDTO.availableCopies());
        book.setAuthors(new HashSet<>());

        Book savedBook = bookService.saveBookWithAuthors(book, bookRequestDTO.authorIds());

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedBook));
    }

    private BookResponseDTO convertToDto(Book book) {
        Set<AuthorResponseDTO> authorDtos = book.getAuthors().stream()
                .map(author -> new AuthorResponseDTO(author.getId(), author.getName(), author.getBiography()))
                .collect(Collectors.toSet());

        return new BookResponseDTO(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getGenre(),
                book.getAvailableCopies(),
                authorDtos
        );
    }
}
