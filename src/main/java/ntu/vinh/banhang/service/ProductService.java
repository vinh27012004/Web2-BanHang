package ntu.vinh.banhang.service;

import java.util.List;

import ntu.vinh.banhang.entity.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product getProductByCode(String code);
    Product saveProduct(Product product);
    void deleteProduct(Long id);
    void addToCart(Long id, Integer quantity);
} 