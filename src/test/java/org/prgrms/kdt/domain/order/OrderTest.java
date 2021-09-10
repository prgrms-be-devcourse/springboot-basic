package org.prgrms.kdt.domain.order;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OrderTest {
    static final Long customerId = new RandomDataGenerator().nextLong(0, 10000);
    final Voucher fixedAmountVoucher = new FixedAmountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.FIXED_AMOUNT, 10L);
    final Voucher percentDiscountVoucher = new PercentDiscountVoucher(new RandomDataGenerator().nextLong(0, 10000), VoucherType.PERCENT_DISCOUNT, 10L);

    static Stream<Arguments> orderSource() {
        return Stream.of(arguments(
                new RandomDataGenerator().nextLong(0, 10000), customerId, Lists.list(
                        new OrderItem(new RandomDataGenerator().nextLong(0, 10000), 100L, 1L),
                        new OrderItem(new RandomDataGenerator().nextLong(0, 10000), 200L, 1L)
                )
        ));
    }

    @ParameterizedTest
    @DisplayName("주문 생성자 테스트 - voucher 없을 때")
    @MethodSource("orderSource")
    void createConstructorWithoutVoucher(Long orderId, Long customerId, List<OrderItem> orderItems) {
        Order order = new Order(orderId, customerId, orderItems);
        assertThat(order).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("주문 생성자 테스트 - voucher 있을 때")
    @MethodSource("orderSource")
    void createConstructorWithVoucher(Long orderId, Long customerId, List<OrderItem> orderItems) {
        Order orderFixed = new Order(orderId, customerId, orderItems, fixedAmountVoucher);
        Order orderPercent = new Order(orderId, customerId, orderItems, percentDiscountVoucher);
        assertAll(
                () -> assertThat(orderFixed).isNotNull(),
                () -> assertThat(orderPercent).isNotNull()
        );
    }

    @ParameterizedTest
    @DisplayName("최종 금액 확인 테스트")
    @MethodSource("orderSource")
    void totalAmount(Long orderId, Long customerId, List<OrderItem> orderItems) {
        Order orderByFixedVoucher = new Order(orderId, customerId, orderItems, fixedAmountVoucher);
        Order orderByPercentVoucher = new Order(orderId, customerId, orderItems, percentDiscountVoucher);
        assertAll(
                () -> assertThat(orderByFixedVoucher.totalAmount()).isEqualTo(290L),
                () -> assertThat(orderByPercentVoucher.totalAmount()).isEqualTo(270L)
        );
    }
}
