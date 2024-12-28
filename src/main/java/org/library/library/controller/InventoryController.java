package org.library.library.controller;

import jakarta.validation.Valid;
import org.library.library.dto.QuantityAdjustmentDto;
import org.library.library.service.InventoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/list")
    public String listInventory(Model model,
                                @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("inventories",
                inventoryService.getInventories(PageRequest.of(page, 10)));
        return "admin/inventory-list";
    }

    @GetMapping("/books/{isbn}")
    public String showInventoryDetails(@PathVariable String isbn, Model model) {
        model.addAttribute("inventory", inventoryService.getInventory(isbn));
        return "admin/inventory-details";
    }

    @GetMapping("/books/{isbn}/adjust")
    public String showAdjustmentForm(@PathVariable String isbn, Model model) {
        model.addAttribute("adjustmentDTO", new QuantityAdjustmentDto());
        model.addAttribute("inventory", inventoryService.getInventory(isbn));
        return "admin/inventory-adjust";
    }

    @PostMapping("/books/{isbn}/adjust")
    public String adjustQuantity(@PathVariable String isbn,
                                 @Valid @ModelAttribute QuantityAdjustmentDto adjustmentDTO,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "inventory/adjust";
        }
        inventoryService.adjustQuantity(isbn, adjustmentDTO);
        return "redirect:/inventory";
    }
}
