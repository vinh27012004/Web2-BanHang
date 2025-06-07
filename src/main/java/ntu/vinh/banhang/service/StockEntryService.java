package ntu.vinh.banhang.service;

import ntu.vinh.banhang.entity.Product;
import ntu.vinh.banhang.entity.StockEntry;

import java.util.List;

public interface StockEntryService {
    List<StockEntry> getAllEntries();
    void addStockEntry(StockEntry stockEntry);
    List<StockEntry> getEntriesByProduct(Product product);
} 