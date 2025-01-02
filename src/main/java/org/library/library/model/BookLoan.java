package org.library.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    @ToString.Exclude
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @ToString.Exclude
    private AppUser borrower;

    private Date borrowedAt;
    private Date dueDate;
    private Date returnedAt;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;


}
