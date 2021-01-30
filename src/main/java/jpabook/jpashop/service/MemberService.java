package jpabook.jpashop.service;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
//    @Autowired
//    MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    private final MemberRepository memberRepository;

    /**
     * 회원가입하기 전에 중복체크
     */
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 가입
     * @Transactional 어노테이션을 선언하지 않으면 아래와 같은 에러 발생한다.
     * No EntityManager with actual transaction available for current thread
     * - cannot reliably process 'persist' call; nested exception is
     * javax.persistence.TransactionRequiredException:
     * No EntityManager with actual transaction available for current thread
     * - cannot reliably process 'persist' call
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        //return memberRepository.findOne(memberId);
        return memberRepository.findById(memberId).get();
    }
}