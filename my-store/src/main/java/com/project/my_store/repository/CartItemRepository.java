package com.project.my_store.repository;

import com.project.my_store.model.Cart;
import com.project.my_store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}

