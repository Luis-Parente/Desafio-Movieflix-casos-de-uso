package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("Select obj FROM Review obj WHERE obj.movie.id = :movieId")
	List<Review> getReviews(Long movieId);
}
