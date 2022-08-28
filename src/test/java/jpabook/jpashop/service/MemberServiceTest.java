package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)  //junit 실행할 때 스프링이랑 같이 엮어서 실행
@SpringBootTest  //스프링부트를 띄운 상태로 테스트 돌릴 수 있음
@Transactional  //이거 있어야 롤백 가능
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(false)  //@Transactional은 트랜젝션 커밋을 안 하고 롤백을 하기 때문에 rollback(false)로 설정시 롤백 안 하고 커밋해버림..insert 커밋 볼 수 있음
    //ㄴ또는 entitymanager를 사용하여 em.flush()로 insert문 볼 수 있고 실제 트랜젝션은 롤백을 함
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //try-catch로 사용하면 코드가 약간 길고 더럽기 때문에 @test에 (expected = IllegalStateException.class) 설정해주면 깔끔
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
       fail("예외가 발생해야 한다!");
    }
}