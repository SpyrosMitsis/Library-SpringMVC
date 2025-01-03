package org.library.library.repository;

import org.library.library.dto.BookListDto;
import org.library.library.dto.RatingSummaryDto;
import org.library.library.model.Rating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository  extends JpaRepository<Rating, Long> {

    List<Rating> findByUserUsername(String username);
    List<Rating> findByBookIsbn(String isbn);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.book.isbn = :isbn")
    Float findAverageRatingByBookIsbn(String isbn);



    @Query("""
            SELECT new org.library.library.dto.RatingSummaryDto(
                AVG(r.score) as avgScore,
                COUNT(r.book.isbn) as ratingCount,
                r.book.isbn
            )
            FROM Rating r
            GROUP BY r.book.isbn, r.book.title
            ORDER BY avgScore DESC
            """)
    List<RatingSummaryDto> findHighestRatingSummary();
}
