package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.*;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("새롭게 추가된 바우처를 넣고 이 아이디로 검색한 결과, 새롭게 추가된 바우처와 검색한 결과의 바우처는 같다.")
    void findById_InsertVoucher_EqualsReturnVoucher() {
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
    @DisplayName("존재하지 않은 바우처를 아이디로 검색했을 때 빈값을 반환한다.")
    void findById_NonExistingVoucherId_ReturnsEmptyOptional() {
        //when
        Optional<Voucher> result = voucherRepository.findById(voucherId);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("추가된 바우처와 추가하면서 반환한 바우처는 같다.")
    void insert_InsertedVoucher_EqualsReturnVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(voucherId, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);

        //when
        Voucher result = voucherRepository.insert(voucher);

        //then
        assertThat(result).isNotNull()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("모든 바우처를 조회했을 때 추가한 바우처의 목록과 같다.")
    void getAllVoucher_AllVouchers_SameContents() {
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
