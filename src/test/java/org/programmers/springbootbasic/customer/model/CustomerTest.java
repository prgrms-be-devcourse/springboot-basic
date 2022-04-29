package org.programmers.springbootbasic.customer.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.exception.CustomInvalidNameException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Customer 클래스")
class CustomerTest {

    @Nested
    @DisplayName("validateName 메소드는")
    class ValidateNameOf {

        @Nested
        @DisplayName("이름이 비어있을 때")
        class ContextWithBlankName {

            @Test
            @DisplayName("예외를 반환합니다")
            void it_return_a_throw() {
                assertThatThrownBy(() -> {
                    new Customer(UUID.randomUUID(), "", LocalDateTime.now());
                })
                        .isInstanceOf(CustomInvalidNameException.class)
                        .hasMessageContaining("Name should not be blank");
            }
        }

        @Nested
        @DisplayName("이름이 비어있지 않을 때")
        class ContextWithNoneBlankName {

            @Test
            @DisplayName("customer를 반환합니다")
            void it_return_a_customer() {
                Customer newCustomer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

                assertThat(newCustomer).isNotNull();
            }
        }
    }
}