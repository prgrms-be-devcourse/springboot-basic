package com.prgrms.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.PercentDiscountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.model.voucher.discount.PercentDiscount;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MemoryVoucherRepositoryTest {

    private static final int FIX_VOUCHER_ID = 1;
    private static final int PERCENT_VOUCHER_ID = 2;
    private static final int NOT_EXIST_VOUCHER_ID = 2;
    private MemoryVoucherRepository voucherRepository;
    private Voucher voucher;

    @BeforeEach
    void setUp() {
        voucher = new FixedAmountVoucher(FIX_VOUCHER_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER);
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.insert(voucher);
    }

    @Test
    @DisplayName("새롭게 추가된 바우처를 넣고 이 아이디로 검색한 결과, 새롭게 추가된 바우처와 검색한 결과의 바우처는 같다.")
    void findById_InsertVoucher_EqualsReturnVoucher() {
        //when
        Optional<Voucher> result = voucherRepository.findById(FIX_VOUCHER_ID);

        //then
        assertThat(result.get()).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("존재하지 않은 바우처를 아이디로 검색했을 때 빈값을 반환한다.")
    void findById_NonExistingVoucherId_ReturnsEmptyOptional() {
        //when
        Optional<Voucher> result = voucherRepository.findById(NOT_EXIST_VOUCHER_ID);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("추가된 바우처와 추가하면서 반환한 바우처는 같다.")
    void insert_InsertedVoucher_EqualsReturnVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(FIX_VOUCHER_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER);

        //when
        Voucher result = voucherRepository.insert(voucher);

        //then
        assertThat(result).isNotNull()
                .isEqualTo(voucher);
    }

    @ParameterizedTest
    @DisplayName("모든 바우처를 조회했을 때 추가한 바우처의 목록과 같다.")
    @MethodSource("voucherProvider")
    void getAllVoucher_AllVouchers_SameContents(List<Voucher> voucherList) {
        //given
        voucherList.forEach(voucherRepository::insert);

        //when
        Vouchers result = voucherRepository.getAllVoucher();

        //then
        assertThat(result.vouchers())
                .isNotNull()
                .containsExactlyInAnyOrderElementsOf(voucherList);
    }

    private static Stream<List<Voucher>> voucherProvider() {
        Voucher createdVoucher1 = new FixedAmountVoucher(FIX_VOUCHER_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(PERCENT_VOUCHER_ID,
                new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);
        return Stream.of(
                List.of(createdVoucher1, createdVoucher2)
        );
    }
}
