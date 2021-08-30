package org.prgrms.kdt.devcourse.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.devcourse.voucher.FixedAmountVoucher;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("고객 이름과 이메일은 빈칸일 수 없다.")
    void testValidateName() {
        assertAll("Customer creation(with empty name or email)",
                ()->assertThrows(IllegalArgumentException.class, ()->new Customer(UUID.randomUUID(),"","testUser@gmail.com", LocalDateTime.now())),
                ()->assertThrows(IllegalArgumentException.class, ()->new Customer(UUID.randomUUID(),"testUserName","", LocalDateTime.now()))
        );

    }

    @Test
    @DisplayName("고객 이름을 변경할 수 있다.")
    void testChangeName() {
        //GIVEN
        Customer originCustomer = new Customer(UUID.randomUUID(), "originName", "origin@gamil.com", LocalDateTime.now());

        //WHEN
        originCustomer.changeName("newName");

        //THEN
        assertThat(originCustomer.getName(),is("newName"));
    }

    @Test
    @DisplayName("고객 이름을 빈칸으로 변경할 수 없다.")
    void testValidateChangeName() {
        Customer originCustomer = new Customer(UUID.randomUUID(), "originName", "origin@gamil.com", LocalDateTime.now());

        assertAll("Customer creation(with empty name or email)",
                ()->assertThrows(IllegalArgumentException.class, ()-> originCustomer.changeName("")),
                ()->assertThrows(IllegalArgumentException.class, ()-> originCustomer.changeName("     ")),
                ()->assertThrows(IllegalArgumentException.class, ()-> originCustomer.changeName("   "))
        );

       
    }
}