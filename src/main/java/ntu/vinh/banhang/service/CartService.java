package ntu.vinh.banhang.service;

import java.util.List;

import ntu.vinh.banhang.model.CartItem;

public interface CartService {
    void addToCart(Long productId, Integer quantity);
    void removeFromCart(Long productId);
    void updateQuantity(Long productId, Integer quantity);
    List<CartItem> getCartItems();
    Double getTotalAmount();
    void clearCart();
} 