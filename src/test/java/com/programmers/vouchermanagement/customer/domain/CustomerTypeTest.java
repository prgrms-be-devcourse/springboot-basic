package com.programmers.vouchermanagement.customer.domain;

import com.programmers.vouchermanagement.customer.exception.InvalidCustomerTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTypeTest {

    @Nested
    @DisplayName("고객 유형에 적합한 CustomerType 을 반환한다.")
    class fromSuccess {

        @Test
        @DisplayName("white 가 들어온 경우")
        void whenWhiteComeInto() {
            // given
            String type = "white";

            // when
            CustomerType result = CustomerType.from(type);

            // then
            assertThat(result).isEqualTo(CustomerType.WHITE);
        }

        @Test
        @DisplayName("black 가 들어온 경우")
        void whenBlackComeInto() {
            // given
            String type = "black";

            // when
            CustomerType result = CustomerType.from(type);

            // then
            assertThat(result).isEqualTo(CustomerType.BLACK);
        }
    }


    @Test
    @DisplayName("존재하지 않는 고객 유형이 들어오면 에외가 발생한다.")
    void fromException() {
        // given
        String type = "red";

        // given & then
        assertThatThrownBy(() -> CustomerType.from(type))
                .isInstanceOf(InvalidCustomerTypeException.class)
                .hasMessage("잘못된 고객 타입입니다.");
    }
}