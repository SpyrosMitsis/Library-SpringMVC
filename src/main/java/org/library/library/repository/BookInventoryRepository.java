package org.library.library.repository;

import org.library.library.model.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {
    List<BookInventory> findByBookIsbn(String isbn);
}
