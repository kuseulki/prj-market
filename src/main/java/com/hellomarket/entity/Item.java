package com.hellomarket.entity;

import com.hellomarket.dto.ItemFormDto;
import com.hellomarket.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter @Getter
@Entity @Table(name="item")
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;                // 상품코드

    @Column(nullable = false)
    private String itemName;        // 상품명

    @Column(nullable = false)
    private int price;              // 가격

    @Column(nullable = false)
    private int stock;              // 재고 수량

    @Column(nullable = false)
    private String itemDetail;      //상품 상세 설명

    private String imgName;         // 이미지 파일명

    private String imgPath;          // 이미지 조회 경로

    public void updateItem(ItemFormDto itemFormDto){
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stock = itemFormDto.getStock();
        this.itemDetail = itemFormDto.getItemDetail();
    }

    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stock + ")");
        }
        this.stock = restStock;
    }

    public void addStock(int stockNumber){
        this.stock += stockNumber;
    }
}