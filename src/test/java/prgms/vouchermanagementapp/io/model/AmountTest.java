package prgms.vouchermanagementapp.io.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prgms.vouchermanagementapp.domain.model.Amount;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    @DisplayName("금액을 음수로 지정하면 IllegalArgumentException이 발생한다.")
    @Test
    void should_throw_exception_for_negative_amount() {
        // given
        long amount = -3000;

        // when

        // then
        assertThatThrownBy(() -> {
            new Amount(amount);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}