package com.hellomarket.repository;

import com.hellomarket.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long id);
    Cart findCartByMemberId(Long id);
}