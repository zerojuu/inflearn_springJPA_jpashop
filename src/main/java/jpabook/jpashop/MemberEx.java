package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class MemberEx {

    @Id @GeneratedValue //식별자는 Id로 하고 자동생성돠게 generatedvalue
    private Long id;
    private String username;
}
