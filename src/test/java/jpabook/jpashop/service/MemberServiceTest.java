package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    /*
        테스트를 수행한후에 db에 insert 된것을 확인하고 싶으면 메서드위에 아래와 같이
        선언해주면 된다.
        @Rollback(false)
        test/resources 아래에 application.yml 파일이 있으면 이것을 확인할 수 없다.
        insert된것을 확인한 후에 test/resources/application.yml 파일을 만들고
        메모리DB를 사용할 있다는 점을 설명하면 된다.
     */
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("boot");
        //When
        Long saveId = memberService.join(member);
        //Then
        //em.flush();

        //assertEquals(member, memberRepository.findOne(saveId));
        assertEquals(member, memberRepository.findById(saveId).get());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("boot");

        Member member2 = new Member();
        member2.setName("boot");

        //When
        memberService.join(member1);
        /*
        *  아래와 같이 작성하면 코드가 너무 지저분하기 때문에
        * @Test(expected = IllegalStateException.class)로 해주면 깔끔하다.
        */
//        try{
//            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }
        memberService.join(member2);

        //Then
        fail("예외가 발생해야 한다.");
    }
}