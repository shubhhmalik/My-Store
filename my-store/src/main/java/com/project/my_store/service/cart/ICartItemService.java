package com.project.my_store.service.cart;

import com.project.my_store.model.Cart;
import com.project.my_store.model.CartItem;

import java.math.BigDecimal;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
