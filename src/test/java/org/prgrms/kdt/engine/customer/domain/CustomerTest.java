package org.prgrms.kdt.engine.customer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.engine.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CustomerTest {

    @Test
    @DisplayName("유효한 이름으로만 고객을 생성할 수 있다")
    void testValidateName() {
        var signupCustomer = new Customer(UUID.randomUUID(), "signup", "email@test.com", LocalDateTime.now());
        var loginCustomer = new Customer(UUID.randomUUID(), "login", "email@test.com", LocalDateTime.now(), LocalDateTime.now());
        assertThat(signupCustomer.getName(), is("signup"));
        assertThat(loginCustomer.getName(), is("login"));

        assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), "", "email@test.com", LocalDateTime.now()));
        assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), " ", "email@test.com", LocalDateTime.now()));
        assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), "", "email@test.com", LocalDateTime.now(), LocalDateTime.now()));
        assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), " ", "email@test.com", LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("유효한 이메일로만 고객을 생성할 수 있다")
    void testValidateEmail() {
        var signupCustomer = new Customer(UUID.randomUUID(), "test", "signup@test.com", LocalDateTime.now());
        var loginCustomer = new Customer(UUID.randomUUID(), "test", "login@test.com", LocalDateTime.now(), LocalDateTime.now());
        assertThat(signupCustomer.getEmail(), is("signup@test.com"));
        assertThat(loginCustomer.getEmail(), is("login@test.com"));

        assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), "test", "email", LocalDateTime.now()));
        assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), "test", "email", LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("고객의 이름을 변경할 수 있다")
    void testChangeName() {
        var newCustomer = new Customer(UUID.randomUUID(), "test", "example@test.com", LocalDateTime.now());
        newCustomer.changeName("update");
        assertThat(newCustomer.getName(), is("update"));
    }
}
