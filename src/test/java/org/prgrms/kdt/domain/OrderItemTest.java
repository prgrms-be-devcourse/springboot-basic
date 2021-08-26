package org.prgrms.kdt.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.order.OrderItem;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    @DisplayName("주문 아이템 생성 테스트")
    void createConstructor() {
        assertThat(new OrderItem(UUID.randomUUID(), 100L, 1L)).isNotNull();
    }

}
