package com.hellomarket.repository;

import com.hellomarket.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemById(Long id);
}