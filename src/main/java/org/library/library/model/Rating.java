package org.library.library.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rating {
    @Id
    private Long ratingId;
    @ManyToOne
    private AppUser user;
    @ManyToOne
    private Book book;
    @Column(nullable = false)
    private Float score;

    public void setScore(Float score) {
        if (score < 0 || score > 5) {
            throw new IllegalArgumentException("Score must be between 0 and 5");
        }
        this.score = score;
    }
}