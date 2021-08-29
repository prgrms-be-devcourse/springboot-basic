package org.prgrms.orderapp.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CustomerTest {

    @Test
    @DisplayName("Customer의 아이디를 가져올 수 있다.")
    void testGetId() {
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "test", "test address", 10);

        assertThat(customer.getCustomerId(), is(uuid));
    }

    @Test
    @DisplayName("Customer를 블랙리스트로 등록할 수 있다.")
    void testSetBlacklist() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test address", 10);
        assertThat(customer.isBlacklisted(), is(false));

        customer.setBlacklisted(true);
        assertThat(customer.isBlacklisted(), is(true));
    }

    @Test
    @DisplayName("String으로 Customer를 만들 수 있다.")
    void testCreateFromString() {
        String customerString = "fbb2ae1c-864f-41e1-a30a-d80beccb25ca,\"John Doe\",\"Seoul, South Korea\",25";
        Customer customer = new Customer(
                UUID.fromString("fbb2ae1c-864f-41e1-a30a-d80beccb25ca"),
                "John Doe",
                "Seoul, South Korea",
                25);

        Optional<Customer> retrievedCustomer = Customer.createFromString(customerString);

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }
}