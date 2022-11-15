package org.prgrms.springorder.domain.customer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

    private UUID customerId = UUID.randomUUID();
    private String name = "customerName";
    private String email = "customerEmail@gmail.com";

    @DisplayName("name 변경 테스트 - Customer 의 name이 바뀐다.")
    @Test
    void changeNameSuccessTest() {
        //given
        String changeName = "changeName";
        Customer customer = new Customer(customerId, name, email);

        //when
        customer.changeName(changeName);

        //then
        assertEquals(changeName, customer.getName());
        assertEquals(customerId, customer.getCustomerId());
        assertEquals(email, customer.getEmail());
    }

    @DisplayName("name 변경 실패 테스트 - changeName의 파라미터가 빈 값이면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  ", "    ",})
    void changeNameFailTest(String emptyValue) {
        //given
        Customer customer = new Customer(customerId, name, email);

        //when & then
        assertThrows(RuntimeException.class, () -> customer.changeName(emptyValue));
    }

    @DisplayName("lastLoginAt 변경 테스트 - Customer 의 lastLoginAt 이 바뀐다.")
    @Test
    void updateLastLoginAtSuccessTest() {
        //given
        LocalDateTime changeLastLoginAt = LocalDateTime.now();

        Customer customer = new Customer(customerId, name, email);

        //when
        customer.updateLastLoginAt(changeLastLoginAt);

        //then
        assertEquals(changeLastLoginAt, customer.getLastLoginAt());
        assertEquals(name, customer.getName());
        assertEquals(customerId, customer.getCustomerId());
        assertEquals(email, customer.getEmail());
    }

    @DisplayName("block 테스트 - Customer 의 status 가 blocked 으로 바뀐다.")
    @Test
    void statusChangeSuccessTest() {
        //given
        Customer customer = new Customer(customerId, name, email);
        CustomerStatus block = CustomerStatus.BLOCKED;

        //when
        customer.block(block);

        //then
        assertEquals(block, customer.getCustomerStatus());
        assertEquals(name, customer.getName());
        assertEquals(customerId, customer.getCustomerId());
        assertEquals(email, customer.getEmail());
    }
}