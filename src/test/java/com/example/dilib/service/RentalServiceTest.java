package com.example.dilib.service;

import com.example.dilib.exception.ResourceNotFoundException;
import com.example.dilib.model.Book;
import com.example.dilib.model.Rental;
import com.example.dilib.model.RentalStatus;
import com.example.dilib.repository.BookRepository;
import com.example.dilib.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RentalService rentalService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("The Title");
        testBook.setIsbn("1231231231231");
        testBook.setAvailableCopies(2);
    }

    @Test
    void borrowBook_ValidInput_ReturnsSavedRental() {

        Long userId = 101L;
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);
        when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rental result = rentalService.borrowBook(userId, bookId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(RentalStatus.ACTIVE, result.getStatus());
        assertEquals(1, testBook.getAvailableCopies()); // Inventory should be decremented by 1

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(testBook);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void borrowBook_DoesntExists_ThrowsException() {

        Long userId = 101L;
        Long bookId = 99L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            rentalService.borrowBook(userId, bookId);
        });

        verify(bookRepository, never()).save(any(Book.class));
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void borrowBook_OutOfStock_ThrowsException() {

        Long userId = 101L;
        Long bookId = 1L;
        testBook.setAvailableCopies(0);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            rentalService.borrowBook(userId, bookId);
        });

        assertTrue(exception.getMessage().contains("out of stock"));
        verify(rentalRepository, never()).save(any(Rental.class));
    }
}
