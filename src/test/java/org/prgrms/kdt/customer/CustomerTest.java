package org.prgrms.kdt.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exceptions.CustomerException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("이름은 빈 값일 수 없다.")
    void test() {
        // given
        String empty_name = "";

        // when & then
        assertThrows(CustomerException.class, () -> new Customer(UUID.randomUUID().toString(), empty_name, "test@gmail.com"));
    }

    @Test
    @DisplayName("고객은 이름을 변경할 수 있다.")
    void testChangeName(){
        Customer newCustomer = new Customer(UUID.randomUUID().toString(), "beforeChange", "newUser@gmail.com");
        String newName = "afterChange";

        newCustomer.changeName(newName);

        assertEquals(newCustomer.getName(), newName);
    }

    @Test
    @DisplayName("없거나 빈 값으로 이름을 변경할 수 없다.")
    void testCanNotChangeInvalidName(){
        Customer newCustomer = new Customer(UUID.randomUUID().toString(), "validName", "valide@gmail.com");
        String noValidName = "";

        assertThrows(CustomerException.class, () -> newCustomer.changeName(noValidName));
    }

}