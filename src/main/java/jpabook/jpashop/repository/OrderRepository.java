package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    //저장
    public void save(Order order) {
        em.persist(order);
    }

    //단건 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //검색..추후 예저
//    public List<Order> findAll(OrderSearch orderSearch) {
//
//    }
}
