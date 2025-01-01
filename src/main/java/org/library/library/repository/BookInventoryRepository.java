package org.library.library.repository;

import org.library.library.dto.BookListDto;
import org.library.library.model.Book;
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
    Page<BookInventory> findByBookCategoriesId(Long categoryId, PageRequest pageRequest);
    Page<BookInventory> findByBookTitleContaining(String title, Pageable pageable);
}
