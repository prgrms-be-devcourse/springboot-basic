package com.blessing333.springbasic.component.customer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {

    @DisplayName("Customer 객체 생성 - 성공")
    @Test
    void createCustomerTestWithCorrectParam() {
        String userName = "testName";
        String userEmail = "test@test.com";

        Customer newCustomer = Customer.createNewCustomer(userName, userEmail);

        assertThat(newCustomer.getName()).isEqualTo(userName);
        assertThat(newCustomer.getEmail()).isEqualTo(userEmail);
    }

    @DisplayName("Customer 객체 생성 - 실패 - empty name")
    @Test
    void createCustomerTestWithEmptyName() {
        String userName = "";
        String userEmail = "test@test.com";

        assertThrows(IllegalArgumentException.class, () -> Customer.createNewCustomer(userName, userEmail));
    }

    @DisplayName("Customer 이름 변경 - 성공")
    @Test
    void changeCustomerName() {
        Customer customer = createCustomer("beforeName", "test@test.com");
        String afterName = "afterName";

        customer.changeName(afterName);

        assertThat(customer.getName()).isEqualTo(afterName);
    }

    @DisplayName("Customer 이름 변경 - 실패 - empty name")
    @Test
    void changeCustomerNameWithEmptyName() {
        Customer customer = createCustomer("beforeName", "test@test.com");
        String afterName = "";

        assertThrows(IllegalArgumentException.class, () -> customer.changeName(afterName));
    }


    private Customer createCustomer(String name, String email) {
        return Customer.createNewCustomer(name, email);
    }

}