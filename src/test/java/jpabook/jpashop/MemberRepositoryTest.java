package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)  //spring과 관련된 테스트를 한다는 걸 알려줌
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepositoryEx memberRepositoryEx;

    //tdd하고 탭하면 자동으로 test 메서드 생성.. preferences-live template-custom 해서 필요한 것들 추가하면 됨
    @Test
    @Transactional  //spring에서 제공하는 걸 사용.. 스프링을 기반으로 하기에, transactional이 test 케이스에 있으면 끝난 후 디비를 롤백해버림 -> 롤백 안 하려면 @Rollback(false)
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        MemberEx memberEx = new MemberEx();
        memberEx.setUsername("memberA");

        //when
        Long saveId = memberRepositoryEx.save(memberEx);//단축키) extract에서 변수 뽑아냄 : ctrl+alt+v
        MemberEx findMemberEx = memberRepositoryEx.find(saveId);

        //then
        Assertions.assertThat(findMemberEx.getId()).isEqualTo(memberEx.getId());
        Assertions.assertThat(findMemberEx.getUsername()).isEqualTo(memberEx.getUsername());
        Assertions.assertThat(findMemberEx).isEqualTo(memberEx);
        System.out.println("findMember = member: "+ (findMemberEx == memberEx));  //true..같은 트랜잭션에서 조회 저장하기에 같은 영속성 context안에서는 id가 같으면 같은 엔티티로 식별

    }
}