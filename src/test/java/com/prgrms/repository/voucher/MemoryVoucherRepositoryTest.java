package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherRepositoryTest {

    private MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    public void FindById_ExistingVoucherId_ReturnsVoucher() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
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
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        Optional<Voucher> result = voucherRepository.findById(voucherId);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void Insert_InsertedVoucher_Equal() {
        //given
        Voucher voucher = new FixedAmountVoucher( new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        //when
        Voucher result = voucherRepository.insert(voucher);
        //then
        assertThat(result).isNotNull()
                        .isEqualTo(voucher);
    }

    @Test
    public void GetAllVoucher_AllVouchers_Contains() {
        //given
        Voucher createdVoucher1 = new FixedAmountVoucher(new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);
        voucherRepository.insert(createdVoucher1);
        voucherRepository.insert(createdVoucher2);
        //when
        VoucherRegistry result = voucherRepository.getAllVoucher();

        //then
        assertThat(result.getVoucherRegistry())
                .isNotNull()
                .containsOnly(createdVoucher1,createdVoucher2);
    }
}
