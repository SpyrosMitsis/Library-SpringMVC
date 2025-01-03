package org.library.library.repository;

import org.library.library.model.BookInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    List<BookInventory> findByBookIsbn(String isbn);
    Page<BookInventory> findByBookCategoriesIn(List<Long> categoryIds, PageRequest pageRequest);
    Page<BookInventory> findByBookTitleContainingOrBookIsbnContaining(String title, String isbn, Pageable pageable);
}
