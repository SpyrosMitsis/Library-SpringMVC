package org.library.library.controller;

import lombok.RequiredArgsConstructor;
import org.library.library.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String listInventory(Model model,
                                @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("inventories",
                inventoryService.getInventories(PageRequest.of(page, 10)));
        return "inventory/list";
    }

    @GetMapping("/books/{isbn}")
    public String showInventoryDetails(@PathVariable String isbn, Model model) {
        model.addAttribute("inventory", inventoryService.getInventory(isbn));
        return "inventory/details";
    }

    @GetMapping("/books/{isbn}/adjust")
    public String showAdjustmentForm(@PathVariable String isbn, Model model) {
        model.addAttribute("adjustmentDTO", new QuantityAdjustmentDTO());
        model.addAttribute("inventory", inventoryService.getInventory(isbn));
        return "inventory/adjust";
    }

    @PostMapping("/books/{isbn}/adjust")
    public String adjustQuantity(@PathVariable String isbn,
                                 @Valid @ModelAttribute QuantityAdjustmentDTO adjustmentDTO,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "inventory/adjust";
        }
        inventoryService.adjustQuantity(isbn, adjustmentDTO);
        return "redirect:/inventory";
    }
}
