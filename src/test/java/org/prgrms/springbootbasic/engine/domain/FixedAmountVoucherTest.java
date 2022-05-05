package org.prgrms.springbootbasic.engine.domain;

import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FixedAmountVoucherTest {
    private final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 5000, LocalDateTime.now().withNano(0));

    @Test
    void TestCreateVoucher() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(VoucherException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0, LocalDateTime.now().withNano(0))),
                () -> assertThrows(VoucherException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100, LocalDateTime.now().withNano(0))),
                () -> assertThrows(VoucherException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000001, LocalDateTime.now().withNano(0)))
        );
    }

    @Test
    void TestChangeAmount() {
        voucher.changeValue(80000);
        assertThat(voucher.getValue(), is(80000));
    }

    @Test
    void TestChangeToInvalidValue() {
        assertAll("FixedAmountVoucher change amount to invalid value",
                () -> assertThrows(VoucherException.class, () -> voucher.changeValue(0)),
                () -> assertThrows(VoucherException.class, () -> voucher.changeValue(-100)),
                () -> assertThrows(VoucherException.class, () -> voucher.changeValue(1000001))
        );
    }

    @Test
    void TestAllocateToUser() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@mail.com", LocalDateTime.now().withNano(0));
        voucher.changeOwner(customer);
        assertThat(voucher.getCustomerId().isEmpty(), is(false));
        assertThat(voucher.getCustomerId().get(), is(customer.getCustomerId()));

        voucher.revokeOwner();
        assertThat(voucher.getCustomerId().isEmpty(), is(true));
    }
}