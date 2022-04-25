package org.prgrms.part1.engine.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class CustomerTest {
    private final Customer newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now().withNano(0));

    @Test
    void testChangeName() {
        newCustomer.changeName("changed-name");
        assertThat(newCustomer.getName(), is("changed-name"));
    }

    @Test
    void TestToBlack() {
        newCustomer.toBlack();
        assertThat(newCustomer.getIsBlack(), is(true));
    }

    @Test
    void revokeBlack() {
        newCustomer.revokeBlack();
        assertThat(newCustomer.getIsBlack(), is(false));
    }
}