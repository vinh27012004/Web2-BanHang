package ntu.vinh.banhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ntu.vinh.banhang.entity.StockEntry;
import ntu.vinh.banhang.entity.Product;
import ntu.vinh.banhang.repository.StockEntryRepository;
import ntu.vinh.banhang.repository.ProductRepository;
import ntu.vinh.banhang.service.StockEntryService;
import java.util.List;

@Service
public class StockEntryServiceImpl implements StockEntryService {

    @Autowired
    private StockEntryRepository stockEntryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<StockEntry> getAllEntries() {
        return stockEntryRepository.findAll();
    }

    @Override
    @Transactional
    public void addStockEntry(StockEntry stockEntry) {
        // Cập nhật số lượng sản phẩm
        Product product = stockEntry.getProduct();
        product.setQuantity(product.getQuantity() + stockEntry.getQuantity());
        productRepository.save(product);
        
        // Lưu phiếu nhập kho
        stockEntryRepository.save(stockEntry);
    }

    @Override
    public List<StockEntry> getEntriesByProduct(Product product) {
        return stockEntryRepository.findByProductOrderByCreatedAtDesc(product);
    }
} 