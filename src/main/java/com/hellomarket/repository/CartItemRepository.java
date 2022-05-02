package com.hellomarket.repository;

import com.hellomarket.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
    CartItem findCartItemById(Long id);
    List<CartItem> findCartItemByItemId(Long id);

}