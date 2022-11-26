package com.program.commandLine.customer;

import com.program.commandLine.model.customer.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {


    @Test
    @DisplayName("이름이 공백일 경우 Customer객체가 생성되지않는다.")
    void testValidateName() {
        String blankName = "";
        String email = "youngji@naver.com";

        assertThrows(IllegalArgumentException.class, () -> new RegularCustomer(UUID.randomUUID(), blankName, email));
        assertThrows(IllegalArgumentException.class, () -> new BlackListCustomer(UUID.randomUUID(), blankName, email));
    }

    @Test
    @DisplayName("blacklist 고객은 로그인 할수 없다.")
    void testBlackListCustomerLogin() {
        String name = "영지";
        String email = "youngji@naver.com";
        Customer blackListCustomer = new BlackListCustomer(UUID.randomUUID(), name, email);

        assertThrows(RuntimeException.class, () -> blackListCustomer.login());
    }

    @Test
    @DisplayName("일반 고객 로그인시 lastLogin이 갱신된다.")
    void testRegularCustomerLogin() {
        String name = "영지";
        String email = "youngji@naver.com";
        Customer customer = new RegularCustomer(UUID.randomUUID(), name, email);

        var beforeLoginAt = customer.getLastLoginAt();
        customer.login();
        var afterLoginAt = customer.getLastLoginAt();

        assertThat(beforeLoginAt, not(afterLoginAt));
    }

    @Test
    @DisplayName("CustomerType에 따라 알맞은 Customer이 생성된다.")
    void testCustomerType() {
        var customerFactory = new CustomerFactory();
        var blackListType = CustomerType.BLACK_LIST_CUSTOMER;
        var regularType = CustomerType.REGULAR_CUSTOMER;

        var blackListCustomer = customerFactory.createCustomer(blackListType, UUID.randomUUID(), "영지", "youngji@naver.com");
        var regularCustomer = customerFactory.createCustomer(regularType, UUID.randomUUID(), "영지", "youngji@naver.com");

        assertThat(blackListCustomer.getCustomerType(), is(blackListType));
        assertThat(regularCustomer.getCustomerType(), is(regularType));

    }

}