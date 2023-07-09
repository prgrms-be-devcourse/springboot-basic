package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.dto.discount.FixedDiscount;
import com.prgrms.model.voucher.dto.discount.PercentDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    private MemoryVoucherRepository voucherRepository;
    private int voucherId = 1;

    @BeforeEach
    void setUp() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    public void FindById_ExistingVoucherId_ReturnsVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(voucherId, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);

        voucherRepository.insert(voucher);
        //when
        Optional<Voucher> result = voucherRepository.findById(voucherId);

        //then
        assertThat(result.get()).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    public void FindById_NonExistingVoucherId_ReturnsEmptyOptional() {
        //when
        Optional<Voucher> result = voucherRepository.findById(voucherId);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void Insert_InsertedVoucher_Equal() {
        //given
        Voucher voucher = new FixedAmountVoucher(voucherId, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);

        //when
        Voucher result = voucherRepository.insert(voucher);

        //then
        assertThat(result).isNotNull()
                .isEqualTo(voucher);
    }

    @Test
    public void GetAllVoucher_AllVouchers_Contains() {
        //given
        Voucher createdVoucher1 = new FixedAmountVoucher(voucherId, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId, new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);
        voucherRepository.insert(createdVoucher1);
        voucherRepository.insert(createdVoucher2);

        //when
        Vouchers result = voucherRepository.getAllVoucher();

        //then
        assertThat(result.getVouchers())
                .isNotNull()
                .containsOnly(createdVoucher1, createdVoucher2);
    }
}
