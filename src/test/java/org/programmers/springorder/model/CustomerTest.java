package org.programmers.springorder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    @DisplayName("고객 생성에 성공한다.")
    public void createCustomer() {
        //given
        UUID customerId1 = UUID.randomUUID();
        String name1 = "홍길동";
        CustomerType customerType1 = CustomerType.NORMAL;

        UUID customerId2 = UUID.randomUUID();
        String name2 = "세종대왕";
        CustomerType customerType2 = CustomerType.BLACK;


        //when
        Customer customer1 = Customer.toCustomer(customerId1, name1, customerType1);
        Customer customer2 = Customer.toCustomer(customerId2, name2, customerType2);

        //then
        assertThat(customer1.getCustomerId()).isEqualTo(customerId1);
        assertThat(customer2.getCustomerId()).isEqualTo(customerId2);
    }
}