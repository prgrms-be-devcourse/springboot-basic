package com.example.kdtspringmission.voucher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.customer.domain.Customer;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VoucherTest {

    @Test
    @DisplayName("바우처에 소유자를 추가할 수 있어야 한다")
    void testAddOwner() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "tester1", "tester@gmail.com",
            LocalDateTime.now());
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID());

        // when
        voucher.setOwner(customer);

        System.out.println("voucher = " + voucher.getClass().getSimpleName());

        // then
        assertThat(voucher.getOwner()).isEqualTo(customer);
    }

}
