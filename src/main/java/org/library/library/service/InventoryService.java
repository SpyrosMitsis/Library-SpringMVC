package org.library.library.service;

import org.library.library.dto.QuantityAdjustmentDto;
import org.library.library.model.BookInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface InventoryService {
    Page<BookInventory> getInventories(PageRequest pageRequest);
    BookInventory getInventory(String isbn);
    BookInventory adjustQuantity(String isbn, QuantityAdjustmentDto quantityAdjustmentDto);
}
