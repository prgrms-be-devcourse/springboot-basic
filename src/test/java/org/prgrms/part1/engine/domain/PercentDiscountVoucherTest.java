package org.prgrms.part1.engine.domain;

import org.junit.jupiter.api.Test;
import org.prgrms.part1.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    private final Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now().withNano(0));

    @Test
    void TestCreateVoucher() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(VoucherException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0, LocalDateTime.now().withNano(0))),
                () -> assertThrows(VoucherException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100, LocalDateTime.now().withNano(0))),
                () -> assertThrows(VoucherException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 101, LocalDateTime.now().withNano(0)))
        );
    }

    @Test
    void TestChangeAmount() {
        voucher.changeValue(80);
        assertThat(voucher.getValue(), is(80));
    }

    @Test
    void TestChangeToInvalidValue() {
        assertAll("PercentDiscountVoucher change amount to invalid value",
                () -> assertThrows(VoucherException.class, () -> voucher.changeValue(0)),
                () -> assertThrows(VoucherException.class, () -> voucher.changeValue(-100)),
                () -> assertThrows(VoucherException.class, () -> voucher.changeValue(101))
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