package org.library.library.service.impl;

import org.library.library.model.Rating;
import org.library.library.repository.RatingRepository;
import org.library.library.service.RatingService;

import java.util.List;

public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }
    @Override
    public void delete(Rating rating) {
        ratingRepository.delete(rating);
    }

    @Override
    public Rating findById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }
    @Override
    public List<Rating> findByBookIsbn(String isbn) {
        return ratingRepository.findByBookIsbn(isbn);
    }
    @Override
    public List<Rating> findByUserUsername(String username) {
        return ratingRepository.findByUserUsername(username);
    }
}

