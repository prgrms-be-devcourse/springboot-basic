package org.prgrms.kdt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.FixedAmountVoucher;
import org.prgrms.kdt.model.PercentDiscountVoucher;
import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.VoucherRepository;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @DisplayName("유효한 voucherType으로만 바우처를 생성할 수 있다.")
    @Test
    void createVoucherByValidateVoucherType() {
        assertAll(
                () -> assertThrows(Exception.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 0, 100)),
                () -> assertThrows(Exception.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 3, 100)),
                () -> assertThat(new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 1, 100).getClass(), is(FixedAmountVoucher.class)),
                () -> assertThat(new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 2, 100).getClass(), is(PercentDiscountVoucher.class))
        );


    }

    @DisplayName("유효한 discountAmount로만 생성 할 수 있다.")
    @Test
    void createVoucherByValidateDiscountAmount() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 1, -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 1, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 2, -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 2, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new VoucherService(new MemoryVoucherRepository()).createVoucher(UUID.randomUUID(), 2, 110))

        );

    }


    @DisplayName("바우처가 생성되어야 한다.")
    @Test
    void createVoucher() {
        VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(voucher.getDiscountAmount(), is(100L));
        assertThat(voucher, notNullValue());
    }

    @DisplayName("바우처가 생성되어야 한다. (mock) - voucher : null")
    @Test
    @Disabled
    void createVoucherByMock() {
        VoucherService voucherService = mock(VoucherService.class);

        Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), 1, 100);

        assertThat(voucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(voucher.getDiscountAmount(), is(100L));
        assertThat(voucher, notNullValue());
    }

}