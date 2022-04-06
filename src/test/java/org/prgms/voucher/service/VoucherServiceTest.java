package org.prgms.voucher.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucher.entity.FixedAmountVoucher;
import org.prgms.voucher.entity.PercentDiscountVoucher;
import org.prgms.voucher.entity.Voucher;
import org.prgms.voucher.entity.VoucherType;
import org.prgms.voucher.exception.WrongCommandInputException;
import org.prgms.voucher.exception.WrongDiscountAmountException;
import org.prgms.voucher.exception.WrongDiscountPercentException;
import org.prgms.voucher.repository.VoucherRepository;

class VoucherServiceTest {

    VoucherService voucherService = new VoucherService(new VoucherRepository() {
        @Override
        public Voucher insert(Voucher voucher) {
            return voucher;
        }

        @Override
        public List<Voucher> findAll() {
            try {
                return Arrays.asList(new FixedAmountVoucher(UUID.randomUUID(), 10L),
                    new PercentDiscountVoucher(UUID.randomUUID(), 20L));
            } catch (Exception ignored) {
            }
            return null;
        }
    });

    @DisplayName("FixedAmountVoucerType을 주면 FixedAmountVoucher를 반환한다.")
    @Test
    void create_FixedAmountVoucherType_ReturnFixedAmountVoucher() throws
        WrongDiscountPercentException,
        WrongCommandInputException,
        WrongDiscountAmountException {

        Voucher voucher = voucherService.create(VoucherType.FIXED_AMOUNT, 10L);

        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher.discount(100L)).isEqualTo(90L);
    }

    @DisplayName("PercentDiscountVoucerType을 주면 PercentDiscountVoucher를 반환한다.")
    @Test
    void create_PercentDiscountType_ReturnPercentDiscountVoucher() throws
        WrongDiscountPercentException,
        WrongCommandInputException,
        WrongDiscountAmountException {
        Voucher voucher = voucherService.create(VoucherType.PERCENT_DISCOUNT, 10L);

        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);
        assertThat(voucher.discount(1000L)).isEqualTo(900L);
    }

    @DisplayName("모든 바우처를 반환한다.")
    @Test
    void findAll_ReturnAllVoucher() {
        List<Voucher> vouchers = voucherService.getVouchers();

        assertThat(vouchers).hasSize(2);
    }
}
