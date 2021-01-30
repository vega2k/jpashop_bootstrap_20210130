package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberOrderTest {
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void member_order_Test() throws Exception {
        //Member와 Order 양밯향 관계 테스트
        Member member = new Member();
        member.setName("Boot12");
        em.persist(member);

        Order order = new Order();
        //member.getOrders().add(order);
        order.setMember(member);
        em.persist(order);
    }

    @Test
    @Rollback(false)
    public void member_order_address_Test() throws Exception {
        //Member와 Order 양밯향 관계 테스트
        Member member = new Member();
        member.setName("부트");
        Address address = new Address("서울","성내로","12345");
        member.setAddress(address);
        em.persist(member);

        Order order = new Order();
        order.setMember(member);
        em.persist(order);
    }

    @Test
    @Rollback(false)
    public void member_order_address_delivery_Test() throws Exception {
        //Member와 Order 양밯향 관계 테스트
        Member member = new Member();
        member.setName("부트1");
        Address address = new Address("서울","성내로","12345");
        member.setAddress(address);
        em.persist(member);

        Order order = new Order();
        order.setMember(member);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        order.setDelivery(delivery);

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        em.persist(order);

    }

    @Test
    @Rollback(false)
    public void member_order_address_delivery_orderitem_Test() throws Exception {
        //Member와 Order 양밯향 관계 테스트
        Member member = new Member();
        member.setName("부트12");
        Address address = new Address("서울","성내로","12345");
        member.setAddress(address);
        em.persist(member);

        Order order = new Order();
        order.setMember(member);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        order.setDelivery(delivery);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderPrice(1000);
        orderItem.setCount(10);

        order.addOrderItem(orderItem);

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        em.persist(order);

    }

    @Test
    @Rollback(false)
    public void member_order_address_delivery_orderitem_item_Test() throws Exception {
        //Member와 Order 양밯향 관계 테스트
        Member member = new Member();
        member.setName("부트22");
        Address address = new Address("서울","성내로","12345");
        member.setAddress(address);
        em.persist(member);

        Order order = new Order();
        order.setMember(member);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        order.setDelivery(delivery);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderPrice(1000);
        orderItem.setCount(10);

        Book item = new Book();
        item.setName("파이썬");
        item.setStockQuantity(10);
        item.setPrice(2000);
        item.setAuthor("백명숙");
        item.setIsbn("1234-5678");
        orderItem.setItem(item);

        order.addOrderItem(orderItem);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());


        em.persist(order);

    }

}
