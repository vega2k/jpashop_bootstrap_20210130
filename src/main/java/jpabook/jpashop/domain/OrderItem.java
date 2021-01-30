package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_item")
@Getter @Setter
//기본생성자의 접근제한자를 protected로 한다는 뜻
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;      //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;       //주문
    
    private int orderPrice;  //주문 가격
    
    private int count; //주문 수량

    //==생성 메서드==//
    //OrderService에서 주문상품(OrderItem)을 생성할 때 사용함
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //주문하면 재고가 줄어야 한다.
        item.removeStock(count);
        return orderItem;
    }

    /**
     * 주문 취소
     * Order의 cancel() 메서드에서 호출한다.
     */
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문상품 전체가격 조회
     * Order의 getTotalPrice() 메서드에서 호출한다.
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
