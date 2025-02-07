package com.myecommerce.core.services;

public interface CartService {
    int getCartItemCount(String userId);
    void updateCartCount(String userId, int count);
}
