package com.travelbnb.repository;

import com.travelbnb.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}