package com.example.dilib.repository;

import com.example.dilib.model.Rental;
import com.example.dilib.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserId(Long userId);

    List<Rental> findByStatus(RentalStatus status);

    List<Rental> findByUserIdAndStatus(Long userId, RentalStatus status);
}
