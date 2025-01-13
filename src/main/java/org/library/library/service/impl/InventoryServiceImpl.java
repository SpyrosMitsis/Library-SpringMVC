package org.library.library.service.impl;

import org.library.library.dto.QuantityAdjustmentDto;
import org.library.library.model.BookInventory;
import org.library.library.repository.BookInventoryRepository;
import org.library.library.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final BookInventoryRepository bookInventoryRepository;

    public InventoryServiceImpl(BookInventoryRepository bookInventoryRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
    }


    @Override
    public Page<BookInventory> getInventories(PageRequest pageRequest) {
        return bookInventoryRepository.findAll(pageRequest);
    }

    @Override
    public BookInventory getInventory(String isbn) {
        return bookInventoryRepository.findByBookIsbn(isbn).stream().findFirst().orElse(null);
    }

    @Override
    public BookInventory adjustQuantity(String isbn, QuantityAdjustmentDto quantityAdjustmentDto) {
        BookInventory bookInventory = bookInventoryRepository.findByBookIsbn(isbn).stream().findFirst().orElse(null);

        assert bookInventory != null;
        Integer difference = bookInventory.getTotalQuantity() - bookInventory.getAvailableQuantity();

        if (quantityAdjustmentDto.getType() == QuantityAdjustmentDto.AdjustmentType.INCREASE) {
            bookInventory.setAvailableQuantity((bookInventory.getTotalQuantity() + quantityAdjustmentDto.getQuantity()) - difference);
            bookInventory.setTotalQuantity(bookInventory.getTotalQuantity() + quantityAdjustmentDto.getQuantity());
        } else if (quantityAdjustmentDto.getType() == QuantityAdjustmentDto.AdjustmentType.DECREASE) {
            bookInventory.setAvailableQuantity((bookInventory.getTotalQuantity() - quantityAdjustmentDto.getQuantity()) - difference);
            bookInventory.setTotalQuantity((bookInventory.getTotalQuantity() - quantityAdjustmentDto.getQuantity()));
        } else if ( quantityAdjustmentDto.getType() == QuantityAdjustmentDto.AdjustmentType.SET) {
            bookInventory.setAvailableQuantity(quantityAdjustmentDto.getQuantity() - difference);
            bookInventory.setTotalQuantity(quantityAdjustmentDto.getQuantity());
        }
        else {
            throw new IllegalArgumentException("Invalid adjustment type");
        }
        return bookInventoryRepository.save(bookInventory);

    }
    @Override
    public void deleteInventory(Long id) {
        bookInventoryRepository.deleteById(id);
    }
}
