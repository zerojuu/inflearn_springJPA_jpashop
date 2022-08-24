package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  //member랑 관계 세팅..다대일
    @JoinColumn(name = "member_id") //FK
    //연관관계 주인용(값 변경은 여기서 이뤄짐)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //cascade = cascade.all : orderitems에 데이터를 널어두고 order를 저장하면 orderitems도 같이 저장됨 (all이기에 delete도 같이 됨)
    // 안 하게 되면 persist로 각각 해줘야 함 persist(orderitemA) persist(orderitemB) persist(orderitemC) persist(order) -> cascade하면 persist(order) 하나면 됨
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //cascade : order를 할 때 delivery도 같이 persist 해줌
    //모든 엔티티는 기본적으로 각각 persist를 해줘야 함 근데 cascade 활용하면 같이 됨
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;  //자바8에서는 hibernate가 자동으로 지원해줌

    @Enumerated(EnumType.STRING)
    private  OrderStatus status;  //enum..주문상태

    //연관관계 편의 매서드..양방향일 때 사용
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    //위의 연관관계 편의 매서드를 사용 안 하고 똑같이 할 경우 아래처럼 해야 됨
//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();
//        member.getOrders().add(order);
//        order.setMember(member);
//    }

    //연관관계 편의 매서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //연관관계 편의 매서드
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
