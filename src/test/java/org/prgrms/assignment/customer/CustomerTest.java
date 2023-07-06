package org.prgrms.assignment.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class CustomerTest {

    @Test
    @DisplayName("changeName은 이름을 바꿀 수 있다.")
    void changeNameTest() {
        Customer customer = new Customer(UUID.randomUUID(), "customer1", "customer1@gamil.com", LocalDateTime.now());
        Assertions.assertThrows(RuntimeException.class, () -> {
            customer.validateChange("customer1");
        });

        Assertions.assertThrows(RuntimeException.class, () -> {
            customer.changeName(" ");
        });
        customer.changeName("customer2");
        Assertions.assertEquals("customer2", customer.getName());
    }

    @Test
    @DisplayName("이름은 빈 칸이 될 수 없다.")
    void inValidNameTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            new Customer(UUID.randomUUID(), " ", "invalidcustomer@gmail.com", LocalDateTime.now());
        });
    }

}