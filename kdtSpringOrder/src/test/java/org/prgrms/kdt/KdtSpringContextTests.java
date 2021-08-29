package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderStatus;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/*
1. @ExtendWith(SpringExtension.class)
 1) junit과 상호작용하여 applicationContext이 만들어지게 한다.
 2) SpringContextFramework를 사용할 수 있게 해준다.

2. @ContextConfiguration()
 1) applicationContext가 어떤식으로 만들어져야하는지만 알려준다.
 2) 빈설정을 한 클래스나 xml 파일을 전달하지 않으면 기본적으로 class 안에 있는 static class Config(@Configuration이 설정된)를 찾는다.

3. @ContextConfiguration(classes = {AppConfiguration.class})
 1) 설정해준 클래스, xml 파일을 로드하고 읽어서 ApplicationContext를 만들어준다.

4. @SpringJUnitConfig
 1) @ContextConfiguration + @ExtendWith(SpringExtension.class) 와 동일한 역할을 한다.

5. SpringJUnitConfig을 이용해서 textContextFramework이 만들어짐 ->
    테스트가 실행될때 Config에 의해 applicationContext가 만들어짐 ->
    @ComponentScan 으로 각각의 서비스와 레포지토리를 빈에 등록하는데 이때,
    @ActiveProfiles를 적용시키고 설정된 profiles에 관련된 bean이 만들어진다.

 */

@SpringJUnitConfig
@ActiveProfiles("dev")
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class Config {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext가 생성 되어야한다.")
    public void testApplicationContext(){
        assertThat(context, notNullValue());
    }

    @Test
    @DisplayName("voucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation() {
        VoucherRepository bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    //OrderServiceTest 테스트는 Mock을 이용,
    //아래 테스트는 실제 객체간 상호협력관계를 통합테스트.
    @Test
    @DisplayName("orderService를 사용해서 주문을 생성할 수 있다.")
    public void testOrderService() {
        //Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.save(fixedAmountVoucher);

        //When
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                fixedAmountVoucher.getVoucherId());

        //Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }

}
