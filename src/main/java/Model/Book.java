package Model;

import jakarta.persistence.*;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {
    @Id
    private String isbn;
    private String title;
    private String description;
    private String coverUrl;
    private Date releaseDate;
    private Boolean isAvailable;
    @OneToMany(mappedBy = "book")
    private Set<Rating> ratings = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories1 = new HashSet<>();
}
