package org.programmers.springorder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @ParameterizedTest
    @CsvSource(value = {"홍길동, NORMAL", "세종대왕, BLACK"})
    @DisplayName("고객 생성에 성공한다.")
    public void createCustomer(String name, CustomerType customerType) {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        Customer customer = Customer.toCustomer(customerId, name, customerType);

        //then
        assertThat(customer.getCustomerId()).isEqualTo(customerId);
        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getCustomerType()).isEqualTo(customerType);
    }
}