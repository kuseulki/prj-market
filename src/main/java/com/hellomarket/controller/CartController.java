package com.hellomarket.controller;

import com.hellomarket.entity.Cart;
import com.hellomarket.entity.CartItem;
import com.hellomarket.entity.Item;
import com.hellomarket.entity.Member;
import com.hellomarket.repository.MemberRepository;
import com.hellomarket.service.CartService;
import com.hellomarket.service.ItemService;
import com.hellomarket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final MemberRepository memberRepository;
    private final ItemService itemService;
    private final MemberService memberService;
    private final CartService cartService;

    // 장바구니 페이지 접속
    @GetMapping("/member/cart/{id}")
    public String userCartPage(@PathVariable("id") Long id, Model model, Principal principal) {

        Member member = memberService.findMember(id);
        Cart userCart = member.getCart();
        List<CartItem> cartItemList = cartService.allUserCartView(userCart);

        int totalPrice = 0;
        for (CartItem cartitem : cartItemList) {
            totalPrice += cartitem.getAmount() * cartitem.getItem().getPrice();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCount", userCart.getCount());
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("member", member);

        return "members/userCart";
    }

    @PostMapping("/member/cart/{id}/{itemId}")
    public String addCartItem(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId, int amount) {


        Member member = memberService.findMember(id);
        Item item = itemService.itemView(itemId);
        cartService.addCart(member, item, amount);
        return "redirect:/item/{itemId}";
    }

    // 장바구니에서 물건 삭제 - 삭제하고 남은 상품의 총 개수
    @GetMapping("/member/cart/{id}/{cartItemId}/delete")
    public String deleteCartItem(@PathVariable("id") Long id, @PathVariable("cartItemId") Long itemId, Model model) {

        // itemId로 장바구니 상품 찾기
        CartItem cartItem = cartService.findCartItemById(itemId);

        // 해당 유저의 카트 찾기
        Cart userCart = cartService.findUserCart(id);

        // 장바구니 전체 수량 감소
        userCart.setCount(userCart.getCount() - cartItem.getAmount());

        // 장바구니 물건 삭제
        cartService.cartItemDelete(itemId);

        // 해당 유저의 장바구니 상품들
        List<CartItem> cartItemList = cartService.allUserCartView(userCart);

        // 총 가격 += 수량 * 가격
        int totalPrice = 0;
        for (CartItem cartitem : cartItemList) {
            totalPrice += cartitem.getAmount() * cartitem.getItem().getPrice();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalCount", userCart.getCount());
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("member", memberService.findMember(id));

        return "members/userCart";
    }
}