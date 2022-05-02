package com.hellomarket.service;

import com.hellomarket.entity.Cart;
import com.hellomarket.entity.CartItem;
import com.hellomarket.entity.Item;
import com.hellomarket.entity.Member;
import com.hellomarket.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    // 회원가입 하면 회원당 카트 하나 생성
    public void createCart(Member member){
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);
    }

    // 장바구니 담기
    @Transactional
    public void addCart(Member member, Item newItem, int amount) {

//        String userId = principal.getName(); , Principal principal


        // 유저 id로 해당 유저의 장바구니 찾기
        Cart cart = cartRepository.findByMemberId(member.getId());

        // 장바구니가 존재하지 않는다면
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Item item = itemRepository.findItemById(newItem.getId());
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        // 상품이 장바구니에 존재하지 않는다면 카트상품 생성 후 추가
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item, amount);
            cartItemRepository.save(cartItem);
        }

        // 상품이 장바구니에 이미 존재한다면 수량만 증가
        else {
            CartItem update = cartItem;
            update.setCart(cartItem.getCart());
            update.setItem(cartItem.getItem());
            update.addCount(amount);
            update.setAmount(update.getAmount());
            cartItemRepository.save(update);
        }
        // 카트 상품 총 개수 증가
        cart.setCount(cart.getCount()+amount);
    }

    // 유저 id로 해당 유저의 장바구니 찾기
    public Cart findUserCart(Long userId) {
        return cartRepository.findCartByMemberId(userId);
    }

    // 카트 상품 리스트 중 해당하는 유저가 담은 상품만 반환
    // 유저의 카트 id와 카트상품의 카트 id가 같아야 함
    public List<CartItem> allUserCartView(Cart userCart) {

        // 유저의 카트 id를 꺼냄
        Long userCartId = userCart.getId();

        // id에 해당하는 유저가 담은 상품들 넣어둘 곳
        List<CartItem> UserCartItems = new ArrayList<>();

        // 유저 상관 없이 카트에 있는 상품 모두 불러오기
        List<CartItem> CartItems = cartItemRepository.findAll();

        for(CartItem cartItem : CartItems) {
            if(cartItem.getCart().getId() == userCartId) {
                UserCartItems.add(cartItem);
            }
        }
        return UserCartItems;
    }

    // 카트 상품 리스트 중 해당하는 상품 id의 상품만 반환
    public List<CartItem> findCartItemByItemId(Long id) {
        List<CartItem> cartItems = cartItemRepository.findCartItemByItemId(id);
        return cartItems;
    }

    // 카트 상품 리스트 중 해당하는 상품 id의 상품만 반환
    public CartItem findCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findCartItemById(id);
        return cartItem;
    }

    // 장바구니의 상품 개별 삭제
    public void cartItemDelete(Long id) {
        cartItemRepository.deleteById(id);
    }

}