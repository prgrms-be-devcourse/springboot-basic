package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DurationTest {

    @Test
    void 올바른유효기간_바우처데이트타임생성_예외발생() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMonths(3);

        // when
        Duration duration = new Duration(createdAt, expiredAt);

        // then
        assertThat(duration).isNotNull();
    }

    @Test
    void 잘못된유효기간_바우처데이트타임생성_예외발생() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.minusMonths(3);

        // when && then
        assertThatThrownBy(() -> new Duration(createdAt, expiredAt))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
