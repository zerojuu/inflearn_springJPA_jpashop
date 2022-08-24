package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private  Long id;

    private String name;

    @ManyToMany  //다대다관계
    @JoinTable(name = "category_item",  //일대다, 다대다로 풀어내는 중간 테이블이 필요함
        joinColumns = @JoinColumn(name = "category_id"),  //중간 카테고리아이템 테이블에 있는 컬럼
        inverseJoinColumns = @JoinColumn(name = "item_id"))  //카테고리아이템 테이블에 들어가는 컬럼
    private List<Item> items = new ArrayList<>();

    //연관괸계
    @ManyToOne(fetch = FetchType.LAZY)  //내 부모니까 다대일
    @JoinColumn(name = "parent_id")
    private  Category parent;  //부모

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();  //자식

    //연관관계 편의 매서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
