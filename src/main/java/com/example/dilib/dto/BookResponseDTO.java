package com.example.dilib.dto;

import java.util.Set;

public record BookResponseDTO(
        Long id,
        String isbn,
        String title,
        String genre,
        Integer availableCopies,
        Set<AuthorResponseDTO> authors
) {
}
