package org.library.library.repository;

import org.library.library.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository  extends JpaRepository<Rating, Long> {

    List<Rating> findByUserUsername(String username);
    List<Rating> findByBookIsbn(String isbn);
}
