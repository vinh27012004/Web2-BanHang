package ntu.vinh.banhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ntu.vinh.banhang.entity.StockEntry;
import ntu.vinh.banhang.entity.Product;
import ntu.vinh.banhang.repository.StockEntryRepository;
import ntu.vinh.banhang.repository.ProductRepository;
import ntu.vinh.banhang.service.StockEntryService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockEntryServiceImpl implements StockEntryService {

    @Autowired
    private StockEntryRepository stockEntryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<StockEntry> getAllStockEntries() {
        return stockEntryRepository.findAll();
    }

    @Override
    @Transactional
    public StockEntry createStockEntry(StockEntry entry) {
        if (entry.getQuantity() <= 0) {
            throw new RuntimeException("Số lượng nhập kho phải lớn hơn 0");
        }

        // Set creation time
        entry.setCreatedAt(LocalDateTime.now());

        // Update product quantity
        Product product = productRepository.findById(entry.getProduct().getId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        product.setQuantity(product.getQuantity() + entry.getQuantity());
        productRepository.save(product);

        // Save stock entry
        return stockEntryRepository.save(entry);
    }

    @Override
    public List<StockEntry> getStockEntriesByProduct(Long productId) {
        return stockEntryRepository.findByProductId(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    }
} 