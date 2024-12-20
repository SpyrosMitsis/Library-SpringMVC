package org.library.library.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String isbn;

    private String title;
    private String description;
    private String smallCoverUrl;
    private String bigCoverUrl;
    private Date releaseDate;
    private Boolean isAvailable;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Category> categories = new HashSet<>();

    public Set<Rating> getRatings() {
        return Collections.unmodifiableSet(ratings);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    // Helper methods for managing relationships
    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setBook(this);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setBook(null);
    }
}