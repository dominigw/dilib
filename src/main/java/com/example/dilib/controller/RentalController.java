package com.example.dilib.controller;

import com.example.dilib.model.Rental;
import com.example.dilib.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getUserRentalHistory(userId));
    }

    @PostMapping("/borrow")
    public ResponseEntity<Rental> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        Rental rental = rentalService.borrowBook(userId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Rental> returnBook(@PathVariable Long id) {
        Rental updatedRental = rentalService.returnBook(id);
        return ResponseEntity.ok(updatedRental);
    }
}
