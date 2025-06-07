package ntu.vinh.banhang.repository;

import ntu.vinh.banhang.entity.Product;
import ntu.vinh.banhang.entity.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {
    List<StockEntry> findByProductOrderByCreatedAtDesc(Product product);
}