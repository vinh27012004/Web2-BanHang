package ntu.vinh.banhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ntu.vinh.banhang.entity.StockEntry;
import ntu.vinh.banhang.service.StockEntryService;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockEntryController {

    @Autowired
    private StockEntryService stockEntryService;

    @GetMapping
    public String listStockEntries(Model model) {
        List<StockEntry> entries = stockEntryService.getAllStockEntries();
        model.addAttribute("entries", entries);
        return "stock/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entry", new StockEntry());
        model.addAttribute("products", stockEntryService.getAllProducts());
        return "stock/form";
    }

    @PostMapping("/add")
    public String addStockEntry(@ModelAttribute StockEntry entry) {
        stockEntryService.createStockEntry(entry);
        return "redirect:/stock";
    }

    @GetMapping("/product/{productId}")
    public String viewProductHistory(@PathVariable Long productId, Model model) {
        List<StockEntry> entries = stockEntryService.getStockEntriesByProduct(productId);
        model.addAttribute("entries", entries);
        model.addAttribute("product", stockEntryService.getProductById(productId));
        return "stock/product-history";
    }
} 