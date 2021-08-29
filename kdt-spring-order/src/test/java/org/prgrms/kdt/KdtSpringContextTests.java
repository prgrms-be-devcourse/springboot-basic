package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderStatus;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringJUnitConfig
@ActiveProfiles("dev")
public class KdtSpringContextTests {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order", "org.prgrms.kdt.repository", "org.prgrms.kdt.service"}
    )
    static class Config{
    }

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContext가 생성 되야한다.")
    public void testApplicationContext(){
        assertThat(applicationContext, notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 반으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation(){
        var bean = applicationContext.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    @Test
    @DisplayName("orderService를 사용해서 주문을 생성할 수 있다")
    public void testOrderService(){
//        Given
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(fixedAmountVoucher);

//        When
        Order order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmountVoucher.getVoucherId());

//        Then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));


    }
}
