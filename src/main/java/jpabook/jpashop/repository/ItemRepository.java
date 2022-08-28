package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //상품 저장
    public void save(Item item) {
        if (item.getId() == null) {  //item값은 jpa에 저장하기 전까지 없음.. 즉 값이 null이 아닌 건 디비에 이미 있다는 뜻
            em.persist(item);  //저장
        } else {
            em.merge(item);  //update랑 비슷..추후에 다시 설명
        }
    }

    //아이템 단건 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    //아이템 다건 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
