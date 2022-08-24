package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//repository는 Entity 같은 걸 찾아주는 기능
@Repository
public class MemberRepositoryEx {

    //jpa를 사용하기에 엔티티 매니저가 있어야 한
    @PersistenceContext
    private EntityManager em;

    //저장하는 코드
    public Long save(MemberEx memberEx) {
        em.persist(memberEx);
        return memberEx.getId();
    }

    //조회하는 코드
    public MemberEx find(Long id) {
        return em.find(MemberEx.class, id);
    }
    //test하려면 단축기 shift+ctrl+t for win/linux
}
