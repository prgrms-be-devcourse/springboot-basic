package org.prgrms.kdt.domain.order;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    @Test
    @DisplayName("주문 아이템 생성 테스트")
    void createConstructor() {
        assertThat(new OrderItem(new RandomDataGenerator().nextLong(0, 10000), 100L, 1L)).isNotNull();
    }

}
