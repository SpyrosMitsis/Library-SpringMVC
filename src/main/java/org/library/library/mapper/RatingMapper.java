package org.library.library.mapper;

import org.library.library.dto.BookListDto;
import org.library.library.dto.RatingDto;
import org.library.library.dto.RatingSummaryDto;
import org.library.library.model.Rating;
import org.library.library.service.AppUserService;

import java.util.List;

public class RatingMapper {

    public static RatingDto mapToRatingDto(Rating rating) {
        RatingDto ratingDto = RatingDto.builder()
                .id(rating.getId())
                .score(rating.getScore())
                .isbn(rating.getBook().getIsbn())
                .username(rating.getUser().getUsername())
                .build();
        return ratingDto;
    }
}
