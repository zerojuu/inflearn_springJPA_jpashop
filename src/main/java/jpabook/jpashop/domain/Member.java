package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue  //시퀀스값
    @Column(name = "member_id")  //디비컬럼명
    private Long id;

    private String name;

    @Embedded //내장타입을 포함했다는 어노테이션
    private Address address;

    @OneToMany(mappedBy = "member")  //order입장에서는 일대다 관계, mappedBy는 여기는 order를 맵핑 받은 거라고 선언(즉 읽기 전용)
    private List<Order> orders = new ArrayList<>();
}
