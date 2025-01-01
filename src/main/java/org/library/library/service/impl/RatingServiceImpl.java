package org.library.library.service.impl;

import org.library.library.dto.BookListDto;
import org.library.library.dto.RatingDto;
import org.library.library.dto.RatingSummaryDto;
import org.library.library.mapper.BookMapper;
import org.library.library.mapper.RatingMapper;
import org.library.library.model.Book;
import org.library.library.model.Rating;
import org.library.library.repository.RatingRepository;
import org.library.library.service.AppUserService;
import org.library.library.service.BookService;
import org.library.library.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final AppUserService appUserService;
    private final BookService bookService;

    public RatingServiceImpl(RatingRepository ratingRepository, AppUserService appUserService, BookService bookService) {
        this.ratingRepository = ratingRepository;
        this.appUserService = appUserService;
        this.bookService = bookService;
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
    public List<RatingDto> findByBookIsbn(String isbn) {
        List<Rating> ratings = ratingRepository.findByBookIsbn(isbn);
        return ratings.stream().map(RatingMapper::mapToRatingDto).toList();
    }
    @Override
    public List<RatingDto> findByUserUsername(String username) {
        List<Rating> ratings = ratingRepository.findByUserUsername(username);
        assert ratings != null;
        return ratings.stream().map(RatingMapper::mapToRatingDto).toList();
    }

    @Override
    public float getAverageRating(String isbn) {
        return ratingRepository.findAverageRatingByBookIsbn(isbn);
    }

    @Override
    public void rateBook(RatingDto ratingDto) {
        Rating rating = Rating.builder()
                .score(ratingDto.getScore())
                .user(appUserService.findByUsername(ratingDto.getUsername()))
                .book(bookService.findByIsbn(ratingDto.getIsbn()))
                .build();

        ratingRepository.save(rating);
    }

    @Override
    public List<BookListDto> getHighestRatedBooks() {
        List<RatingSummaryDto> ratingSummaryDto = ratingRepository.findHighestRatingSummary();
        List<Book> books = ratingSummaryDto.stream().map(book -> bookService.findByIsbn(book.getIsbn())).toList();
        return books.stream().map(BookMapper::mapToBookListDto).toList();
    }

}

