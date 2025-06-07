package ntu.vinh.banhang.repository;

import ntu.vinh.banhang.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByCode(String code);
} 