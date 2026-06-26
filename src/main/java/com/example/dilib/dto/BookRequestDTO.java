package com.example.dilib.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

public record BookRequestDTO(
        @NotBlank(message = "ISBN cannot be empty") String isbn,
        @NotBlank(message = "Title cannot be empty") String title,
        String genre,
        @NotNull @PositiveOrZero(message = "Copies must be 0 or more") Integer availableCopies,
        Set<Long> authorIds
) {
}
