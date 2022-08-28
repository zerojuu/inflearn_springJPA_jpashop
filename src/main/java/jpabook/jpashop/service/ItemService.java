package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    //itemService는 단순하게 itemRepository에 위임하는 정도
    //경우에 따라서 단순히 위임만 하는 정도라면 따로 service를 만들지 않고 controller에서 하는 것도 괜찮음

    private final ItemRepository itemRepository;

    //저장
    @Transactional  //위에 readonly true이기에 저장이 안 됨.. 여기를 오버라이딩 해버림
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //조회
    public List<Item> findItem() {
        return itemRepository.findAll();
    }

    //단건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
