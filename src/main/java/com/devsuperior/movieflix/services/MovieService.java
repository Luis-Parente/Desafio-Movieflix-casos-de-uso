package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieDetailsDTO(entity, entity.getGenre());
	}

	public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {
		if (genreId != null && genreId == 0) {
			genreId = null;
		}

		Page<Movie> entities = repository.findByGenre(genreId, pageable);
		return entities.map(x -> new MovieCardDTO(x));
	}

	public List<ReviewDTO> getReviews(Long id) {
		List<Review> list = reviewRepository.getReviews(id);

		return list.stream().map(x -> new ReviewDTO(x)).toList();
	}
}
