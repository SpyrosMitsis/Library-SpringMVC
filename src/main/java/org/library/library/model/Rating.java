package org.library.library.model;

import jakarta.persistence.*;

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

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        if (score < 0 || score > 5) {
            throw new IllegalArgumentException("Score must be between 0 and 5");
        }
        this.score = score;
    }
}