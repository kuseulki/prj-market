//package com.hellomarket.service;
//
//import com.hellomarket.entity.*;
//import com.hellomarket.repository.OrderItemRepository;
//import com.hellomarket.repository.OrderRepository;
//import com.hellomarket.repository.SaleItemRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final MemberService memberService;
//    private final OrderRepository orderRepository;
//    private final OrderItemRepository orderItemRepository;
//    private final SaleItemRepository saleItemRepository;
//    private final ItemService itemService;
//
//    // 회원가입 하면 회원 당 주문 하나 생성
//    public void createOrder(Member user){
//        Order order = Order.createOrder(user);
//        orderRepository.save(order);
//    }
//
//    // id에 해당하는 주문아이템 찾기
//    public List<OrderItem> findUserOrderItems(int userId) {
//        return orderItemRepository.findOrderItemsByUserId(userId);
//    }
//
//    // OrderItem 하나 찾기
//    public OrderItem findOrderitem(int orderItemId) {
//        return orderItemRepository.findOrderItemById(orderItemId);
//    }
//
//    // 장바구니상품주문
//    @Transactional
//    public OrderItem addCartOrder(int itemId, int userId, CartItem cartItem, SaleItem saleItem) {
//        Member user = memberService.findUser(userId);
//        OrderItem orderItem = OrderItem.createOrderItem(itemId, user, cartItem, saleItem);
//        orderItemRepository.save(orderItem);
//        return orderItem;
//    }
//
//    // 주문하면 Order 만들기
//    @Transactional
//    public void addOrder(Member user, List<OrderItem> orderItemList) {
//        Order userOrder = Order.createOrder(user, orderItemList);
//        orderRepository.save(userOrder);
//    }
//
//    // 단일 상품 주문
//    @Transactional
//    public void addOneItemOrder(int userId, Item item, int count, SaleItem saleItem) {
//
//        Member user = memberService.findUser(userId);
//        Order userOrder = Order.createOrder(user);
//        OrderItem orderItem = OrderItem.createOrderItem(item.getId(), user, item, count, userOrder, saleItem);
//        orderItemRepository.save(orderItem);
//        orderRepository.save(userOrder);
//    }
//
//    // 주문 취소 기능
//    @Transactional
//    public void orderCancel(Member user, OrderItem cancelItem) {
//
//        Item item = itemService.itemView(cancelItem.getItemId());
//
//        // 판매자의 판매내역 totalCount 감소
//        cancelItem.getSaleItem().getSale().setTotalCount(cancelItem.getSaleItem().getSale().getTotalCount()-cancelItem.getItemCount());
//
//        // 해당 item 재고 다시 증가
//        item.setStock(item.getStock()+ cancelItem.getItemCount());
//
//        // 해당 item의 판매량 감소
//        item.setCount(item.getCount()-cancelItem.getItemCount());
//
//        // 해당 orderItem의 주문 상태 1로 변경 -> 주문 취소를 의미
//        cancelItem.setIsCancel(cancelItem.getIsCancel()+1);
//
//        // 해당 orderItem.getsaleItemId 로 saleItem 찾아서 판매 상태 1로 변경 -> 판매 취소를 의미
//        cancelItem.getSaleItem().setIsCancel(cancelItem.getSaleItem().getIsCancel()+1);
//
//        orderItemRepository.save(cancelItem);
//        saleItemRepository.save(cancelItem.getSaleItem());
//    }
//}