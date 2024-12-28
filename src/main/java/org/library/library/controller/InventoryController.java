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

    @GetMapping("/{isbn}")
    public String showInventoryDetailsAndAdjustForm(@PathVariable String isbn, Model model) {
        model.addAttribute("inventory", inventoryService.getInventory(isbn));
        model.addAttribute("adjustmentDTO", new QuantityAdjustmentDto());
        return "admin/inventory-detail";
    }


    @PostMapping("/{isbn}/adjust")
    public String adjustQuantity(@PathVariable String isbn,
                                 @Valid @ModelAttribute QuantityAdjustmentDto adjustmentDTO,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("inventory", inventoryService.getInventory(isbn));
            return "admin/inventory-detail?error";
        }
        inventoryService.adjustQuantity(isbn, adjustmentDTO);
        return "redirect:/admin/inventory/" + isbn + "?success=true";
    }
}
