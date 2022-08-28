package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service  //스프링이 제공하는 서비스.. 이거 안에 component가 있어서 자동으로 component scan 대상이 됨
@Transactional(readOnly = true)  // JPA의 모든 데이터 변경이나 로직들은 트랙젠션 안에서 실행해야 함..스프링이 제공하는 어노테이션을 사용하는 게 좋음
@RequiredArgsConstructor  //final이 있는 필드만 가지고 생성자를 만들어줌 (@AllArgsConstructor의 상위 버전 느낌).. lombok
//조회 전용에는 readonly true 설정시 디비에 더 최적화가 됨.. 조회가 아닌 저장,변경 부분에 적용하면 데이터 변경이 안 됨
public class MemberService {

    //@Autowired  //인젝션.. 이 방법(바로 인젝션)을 쓰면 이 부분의 데이터를 변경할 수가 없음 -> setter 인젝션 사용..하지만 이 방법도 안 좋음 -> 생성자 인젝션 사용
    private final MemberRepository memberRepository;  //이 필드를 변경할 일이 없기 때문에 final로 하는 걸 권장..컴파일 시점에 에러 발생 유무를 알 수 있음

    //setter 인젝션 (바로 주입이 아닌 MemberRepository memberRepository 이렇게 한번 들어와서 this.memberRepository = memberRepository 주입을 함)
    //하지만 이 방법 사용시 어플리케이션 실행하고 스프링 실행시 변경 가능
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //생성자 인젝션.. 스프링이 뜰 때 생성자에서 인젝션해줌 그렇기에 중간에 set해서 memberRepository를 변경할 수 없음 또한 테스트케이스 작성시 놀치는 부분이 어딘지 알 수 있음
    //최신 버전의 스프링에서는 생성자가 하나일 경우 Autowired가 없어도 자동으로 인젝션해줌
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     *  회원 가입..member 객체를 넘김
     */
    @Transactional  //상단에 readonly true로 설정했지만 여기에 따로 언급하면 이 부분(readonly false)이 먼저 반영되어 여기에만 따로 적용됨
    public Long join(Member member) {
        validateDuplicateMember(member);  //중복 회원 검증..비즈니스로직
        memberRepository.save(member);  //저장.. memberRepository에서 저장시 member_id를 갖고 있기에 여기에 들고 올 수 있음
        return member.getId();  //아이디 반환
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //같은 이름인 두먕이 동시에 하면(multiThread) 통과될 수 있는 단점이 있음.. 실무에서는 더 고려해야 함 -> 데이터베이스에 멤버의 이름을 유니크 제약 조건으로 잡는 게 좋음
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     *  회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     *  회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
