package com.prgrm.kdt;

import com.prgrm.kdt.order.application.OrderService;
import com.prgrm.kdt.order.domain.Order;
import com.prgrm.kdt.order.domain.OrderItem;
import com.prgrm.kdt.voucher.domain.FixedAmountVoucher;
import com.prgrm.kdt.voucher.domain.Voucher;
import com.prgrm.kdt.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTester {

    @Test
    public void testDefaultOrder() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        UUID customerId = UUID.randomUUID();

        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        OrderService orderService = applicationContext.getBean(OrderService.class);
        Order order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());
        assertThat(order.totalAmount()).isEqualTo(90L);

        applicationContext.close();
    }
}