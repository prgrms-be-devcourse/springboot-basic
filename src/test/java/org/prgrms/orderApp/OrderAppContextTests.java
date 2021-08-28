package org.prgrms.orderApp;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.orderApp.config.component.AppConfiguration;
import org.prgrms.orderApp.domain.order.model.OrderItem;
import org.prgrms.orderApp.domain.order.model.OrderStatus;
import org.prgrms.orderApp.domain.order.service.OrderService;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.repository.VoucherRepository;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.prgrms.orderApp.infrastructure.impl.TempVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
@SpringJUnitConfig
@ActiveProfiles("dev")
public class OrderAppContextTests {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.orderApp"})
    static class config {

    }

    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("applicationContex가 생성되어아 한다. ")
    public void testApplicationContext(){
        assertThat(context, notNullValue());
    }
    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어야 한다.  ")
    public void testVoucherRepositoryCreation(){
        var bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }

    @Test
    @DisplayName("OrderService 를 사용해서 주문을 생성할 수 있다.  ")
    public void testOrderServiceCreation() throws IOException {
        // Given
        var fixedAmoutVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.save(fixedAmoutVoucher);

        // When
        var order = orderService.createOrder(UUID.randomUUID(), List.of(new OrderItem(UUID.randomUUID(), 200, 1)), fixedAmoutVoucher.getVoucherId());

        // Then
        assertThat(order.totalAmount(), equalTo(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmoutVoucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
