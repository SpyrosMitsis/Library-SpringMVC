package org.library.library.repository;

import org.library.library.dto.BookListDto;
import org.library.library.model.BookInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    List<BookInventory> findByBookIsbn(String isbn);
    Page<BookInventory> findByBookCategoriesIn(List<Long> categoryIds, PageRequest pageRequest);
    Page<BookInventory> findByBookTitleContainingOrBookIsbnContaining(String title, String isbn, Pageable pageable);

    @Query(value = """
    SELECT b.isbn,
           b.title,
           b.cover_url,
           b.release_date,
           b.is_available,
           GROUP_CONCAT(DISTINCT a.first_name, ' ', a.last_name) AS authors,
           AVG(r.score) AS avgRating
    FROM book_inventory bi
    LEFT JOIN book b ON b.isbn = bi.book_isbn
    LEFT JOIN rating r ON b.isbn = r.book_isbn
    LEFT JOIN book_category bc ON b.isbn = bc.book_isbn
    LEFT JOIN category c ON bc.category_id = c.id
    LEFT JOIN library.book_author ba on b.isbn = ba.book_isbn
    LEFT JOIN library.author a on ba.author_id = a.id
    WHERE b.isbn IN (
            SELECT DISTINCT bc.book_isbn
            FROM book_category bc
            INNER JOIN category c ON bc.category_id = c.id
            WHERE c.id  IN (:categoryId)
        )
    GROUP BY b.isbn, b.title
    HAVING AVG(r.score) > :minRating
    ORDER BY b.title
    """, nativeQuery = true)
    Page<Object[]> findBooksByRatingsAndCategories(@RequestParam("minRating") Float minRating,
                                         @RequestParam("categoryId") List<Long> categoryId,
                                         PageRequest pageRequest);


    @Query(value = """
    SELECT b.isbn,
           b.title,
           b.cover_url,
           b.release_date,
           b.is_available,
           GROUP_CONCAT(DISTINCT a.first_name, ' ', a.last_name) AS authors,
           AVG(r.score) AS rating
    FROM book_inventory bi
    LEFT JOIN book b ON b.isbn = bi.book_isbn
    LEFT JOIN rating r ON b.isbn = r.book_isbn
    LEFT JOIN book_category bc ON b.isbn = bc.book_isbn
    LEFT JOIN category c ON bc.category_id = c.id
    LEFT JOIN library.book_author ba on b.isbn = ba.book_isbn
    LEFT JOIN library.author a on ba.author_id = a.id
    GROUP BY b.isbn, b.title
    HAVING AVG(r.score) > :minRating
    ORDER BY b.title
    """, nativeQuery = true)
    Page<Object[]> findBooksByRatings(@RequestParam("minRating") Float minRating,
                                                      PageRequest pageRequest);

    }
