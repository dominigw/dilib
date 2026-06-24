package com.example.dilib.service;

import com.example.dilib.exception.ResourceNotFoundException;
import com.example.dilib.model.Book;
import com.example.dilib.model.Rental;
import com.example.dilib.model.RentalStatus;
import com.example.dilib.repository.BookRepository;
import com.example.dilib.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Rental borrowBook(Long userId, Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is currently out of stock.");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Rental rental = new Rental();
        rental.setUserId(userId);
        rental.setBook(book);
        rental.setRentalDate(LocalDate.now());
        rental.setDueDate(LocalDate.now().plusDays(14));
        rental.setStatus(RentalStatus.ACTIVE);

        return rentalRepository.save(rental);
    }

    @Transactional
    public Rental returnBook(Long rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental log entry not found for ID: " + rentalId));

        if (rental.getStatus() == RentalStatus.RETURNED) {
            throw new IllegalStateException("This book has already been returned.");
        }

        Book book = rental.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        rental.setReturnDate(LocalDate.now());
        rental.setStatus(RentalStatus.RETURNED);

        return rentalRepository.save(rental);
    }

    @Transactional(readOnly = true)
    public List<Rental> getUserRentalHistory(Long userId) {
        return rentalRepository.findByUserId(userId);
    }
}
