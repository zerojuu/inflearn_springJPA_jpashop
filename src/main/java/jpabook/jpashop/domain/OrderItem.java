package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) //order과는 다대일
    @JoinColumn(name = "order_id")
    //연관관계 주인
    private Order order;

    private int orderPrice;  //주문가격
    private int count;  //주문수량

    //orderservice에서 주문상품 생성시 기존의 로직이 아닌 OrderItem orderItem = new OrderItem();하고 set 사용하여 생성할 경우 나중에 유지보수 등에
    //안 좋기 때문에 여기서 protected로 해놓으면 orderservice에서 new OrderItem() 이런 식으로 새로운 로직 사용시 빨간줄 생김
    //이 protected 부분을 lombok을 사용해서 @NoArgsConstructor(access = AccessLevel.PROTECTED)로 설정할 수 있음
//    protected OrderItem() {
//    }


    //==생성 매서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);  //count만큼 재고 수정
        return orderItem;
    }

    //==비즈니스 로직==//
    /**
     *  주문 취소시 재고수량 원복
     */
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /**
     *
     *  주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
