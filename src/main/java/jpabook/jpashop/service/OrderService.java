package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    //의존관계
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     *  주문
     */
    @Transactional  //데이터 변경이기에 필요
    private Long order(Long memberId, Long itemId, int count) {  //식별자값 반환
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());  //간단하게 회원의 주소를 set

        //주문상품 생성(단건)
        //OrderItem 클래스에 @NoArgsConstructor(access = AccessLevel.PROTECTED)로 제약 설정
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);  //orderItem의 생성 메서드인 createOrderItem 사용

        //주문 생성
        //Order 클래스에 @NoArgsConstructor(access = AccessLevel.PROTECTED)로 제약 설정
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //Order에서 OrderItem, Delivery를 cascade = CascadeType.ALL 설정했기에 이렇게 하나만 저장해도 자동으로 두 군데도 persist됨
        orderRepository.save(order);

        return order.getId();  //order의 식별자값 반환
    }

    /**
     *  취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소..order 클래스 내의 비즈니스 로직
        order.cancel();
    }

    /**
     *  검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
