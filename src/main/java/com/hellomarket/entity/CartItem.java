package com.hellomarket.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**  장바구니 아이템   */

@Getter @Setter
@Entity @Table(name="cart_item")
public class CartItem {

    @Id  @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private int amount; // 상품 개수

    // 장바구니에 담을 상품 엔티티를 생성하는 메소드와 장바구니에 담을 수량을 증가시켜 주는 메소드
    public static CartItem createCartItem(Cart cart, Item item, int amount) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setAmount(amount);
        return cartItem;
    }

    public void addCount(int amount) {
        this.amount += amount;
    }

    public void updateCount(int amount){
        this.amount = amount;
    }

}