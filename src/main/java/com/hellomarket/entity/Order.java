package com.hellomarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;                       // 구매자

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createDate;           // 주문일

//    public void addOrderItem(OrderItem orderItem) {
//        orderItems.add(orderItem);
//        orderItem.setOrder(this);
//    }

    // 생성메서드
//    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
//        Order order = new Order();
//        order.setMember(member);
//        for (OrderItem orderItem : orderItemList) {
//            order.addOrderItem(orderItem);
//        }
//        order.setCreateDate(order.createDate);
//        return order;
//    }

//    public static Order createOrder(Member member) {
//        Order order = new Order();
//        order.setMember(member);
//        order.setCreateDate(order.createDate);
//        return order;
//    }

    // 주문취소
//    public void cancelOrder() {
//        for (OrderItem orderItem : orderItems) {
//            orderItem.cancel();
//        }
//    }


    // 조회 로직, 전체주문가격조회
//    public int getTotalPrice() {
//        int totalPrice = 0;
//        for(OrderItem orderItem : orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
//    }
}