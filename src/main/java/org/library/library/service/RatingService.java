package org.library.library.service;

import org.library.library.dto.BookListDto;
import org.library.library.dto.RatingDto;
import org.library.library.dto.RatingSummaryDto;
import org.library.library.model.Rating;

import java.util.List;

public interface RatingService {
    void save(Rating rating);

    void delete(Rating rating);

    Rating findById(Long id);

    List<RatingDto> findByBookIsbn(String isbn);
    List<RatingDto> findByUserUsername(String username);
    float getAverageRating(String isbn);
    void rateBook(RatingDto ratingDto);

    List<BookListDto> getHighestRatedBooks();
}
