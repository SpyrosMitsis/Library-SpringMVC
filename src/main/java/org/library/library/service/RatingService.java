package org.library.library.service;

import org.library.library.model.Rating;

import java.util.List;

public interface RatingService {
    void save(Rating rating);

    void delete(Rating rating);

    Rating findById(Long id);

    List<Rating> findByBookIsbn(String isbn);

    List<Rating> findByUserUsername(String username);
}
