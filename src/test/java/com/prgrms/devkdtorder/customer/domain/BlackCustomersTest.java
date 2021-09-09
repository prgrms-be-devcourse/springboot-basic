package com.prgrms.devkdtorder.customer.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class BlackCustomersTest {

    @Test
    @DisplayName("생성시 Black 상태인 고객만 포함된 리스트를 인자로 받아야 한다.")
    void testValueOf() {
        //given
        Customer whiteCustomer = TestCustomerBuilder.start().customerType(CustomerType.BLACK).build();
        Customer blackCustomer = TestCustomerBuilder.start().customerType(CustomerType.BLACK).build();
        var customers = Arrays.asList(whiteCustomer, blackCustomer);
        //when then
        BlackCustomers blackCustomers = BlackCustomers.valueOf(customers);
        assertThat(blackCustomers).isNotNull();
        assertThat(blackCustomers.size()).isEqualTo(2);
        assertThat(blackCustomers, containsInAnyOrder(samePropertyValuesAs(whiteCustomer), samePropertyValuesAs(blackCustomer)));
    }

    @Test
    @DisplayName("생성시 빈 리스트를 인자로 받으면 empty BlackCustomers를 리턴해야 한다")
    void testWhenEmptyInputThenEmptyBlackCustomers() {
        //when
        BlackCustomers blackCustomers = BlackCustomers.valueOf(new ArrayList<>());
        //then
        assertThat(blackCustomers).isNotNull();
        assertThat(blackCustomers.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("생성시 null을 인자로 받으면 예외가 발생해야 한다")
    void testWhenNullInputThenExceptionThrow() {
        assertThatNullPointerException().isThrownBy(() -> BlackCustomers.valueOf(null));
    }

    @Test
    @DisplayName("생성시 Black 상태가 아닌 고객이 포함된 리스트를 인자로 받으면 예외가 발생해야 한다")
    void testWhenNonBlackInputThenExceptionThrow() {
        //given
        Customer whiteCustomer = TestCustomerBuilder.start().customerType(CustomerType.WHITE).build();
        Customer blackCustomer = TestCustomerBuilder.start().customerType(CustomerType.BLACK).build();
        var customers = Arrays.asList(whiteCustomer, blackCustomer);
        //when then
        assertThatIllegalArgumentException().isThrownBy(() -> BlackCustomers.valueOf(customers));
    }


}