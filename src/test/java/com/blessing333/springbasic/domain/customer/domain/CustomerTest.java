package com.blessing333.springbasic.domain.customer.domain;

import com.blessing333.springbasic.domain.customer.CustomerFactory;
import com.blessing333.springbasic.domain.customer.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {

    @DisplayName("Customer 객체 생성 - 성공")
    @Test
    void createCustomerTestWithCorrectParam() {
        UUID id = UUID.randomUUID();
        String userName = "testName";
        String userEmail = "test@test.com";

        Customer newCustomer = createNewCustomer(id,userName, userEmail,LocalDateTime.now());

        assertThat(newCustomer.getName()).isEqualTo(userName);
        assertThat(newCustomer.getEmail()).isEqualTo(userEmail);
    }

    @DisplayName("Customer 객체 생성 - 실패 - empty name")
    @Test
    void createCustomerTestWithEmptyName() {
        UUID id = UUID.randomUUID();
        String userName = "";
        String userEmail = "test@test.com";
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class, () -> createNewCustomer(id,userName, userEmail,now));
    }

    @DisplayName("Customer 이름 변경 - 성공")
    @Test
    void changeCustomerName() {
        Customer customer = CustomerFactory.createCustomer("beforeName", "test@test.com");
        String afterName = "afterName";

        customer.changeName(afterName);

        assertThat(customer.getName()).isEqualTo(afterName);
    }

    @DisplayName("Customer 이름 변경 - 실패 - empty name")
    @Test
    void changeCustomerNameWithEmptyName() {
        Customer customer = CustomerFactory.createCustomer("beforeName", "test@test.com");
        String afterName = "";

        assertThrows(IllegalArgumentException.class, () -> customer.changeName(afterName));
    }

    private Customer createNewCustomer(UUID id,String userName, String userEmail,LocalDateTime time){
        return Customer.customerBuilder()
                .customerId(id)
                .name(userName)
                .email(userEmail)
                .createdAt(time)
                .build();
    }


}