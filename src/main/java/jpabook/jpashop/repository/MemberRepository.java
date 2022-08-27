package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository  //스프링에서 제공하는 repository 어노테이션 -> 컴포넌트스캔 대상이 되서 자동으로 관리됨
@RequiredArgsConstructor
public class MemberRepository {
    //@PersistenceContext  //jpa가 제공하는 표준 어노테이션 -> @RequiredArgsConstructor를 사용하고 final 선언하면 필요없음
    private final EntityManager em;  //@RequiredArgsConstructor를 사용하면서 생성자로 인젝션한 것임

    //저장
    public void save(Member member) {
        em.persist(member);  //저장시 (영속성) member_id를 PK로 보기에 갖고 있음
    }

    //단건 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);  //jpa의 find 매서드
    }

    //리스트 조회
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        //JPQL.. 첫번째는 jpql select문 두번째는 반환타입(Member.class)
        //jpql은 테이블을 대상으로 하는 sql과 다르게 엔티티를 대상으로 쿼리를 짬
        return result;
    }

    //이름으로 조회.. 파라미터를 name으로 넘기기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }
}