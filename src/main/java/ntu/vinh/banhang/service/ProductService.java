package ntu.vinh.banhang.service;

import java.util.List;

import ntu.vinh.banhang.entity.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    void addToCart(Long id, Integer quantity);
} 