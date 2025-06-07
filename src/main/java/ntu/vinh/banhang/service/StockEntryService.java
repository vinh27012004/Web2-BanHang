package ntu.vinh.banhang.service;

import ntu.vinh.banhang.entity.StockEntry;
import ntu.vinh.banhang.entity.Product;
import java.util.List;

public interface StockEntryService {
    List<StockEntry> getAllStockEntries();
    StockEntry createStockEntry(StockEntry entry);
    List<StockEntry> getStockEntriesByProduct(Long productId);
    List<Product> getAllProducts();
    Product getProductById(Long id);
} 