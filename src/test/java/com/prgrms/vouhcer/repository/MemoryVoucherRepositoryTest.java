package com.prgrms.vouhcer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.voucher.model.FixedAmountVoucher;
import com.prgrms.voucher.model.PercentDiscountVoucher;
import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.FixedDiscount;
import com.prgrms.voucher.model.discount.PercentDiscount;
import com.prgrms.voucher.repository.MemoryVoucherRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

class MemoryVoucherRepositoryTest {

    private static final int FIX_VOUCHER_ID = 1;
    private static final int PERCENT_VOUCHER_ID = 2;
    private static final int NOT_EXIST_VOUCHER_ID = 3;


    private final LocalDateTime today = LocalDateTime.now();
    private final LocalDateTime yesterday = today.minusDays(1);
    private final LocalDateTime tomorrow = today.plusDays(1);

    private MemoryVoucherRepository voucherRepository;
    private Voucher fixVoucher;
    private Voucher percemtVoucher;

    @BeforeEach
    void setUp() {
        fixVoucher = new FixedAmountVoucher(FIX_VOUCHER_ID, new FixedDiscount(20),
                VoucherType.FIXED_AMOUNT_VOUCHER, today);
        percemtVoucher = new PercentDiscountVoucher(PERCENT_VOUCHER_ID,
                new PercentDiscount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER, today);

        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.insert(fixVoucher);
        voucherRepository.insert(percemtVoucher);
    }

    @Test
    @DisplayName("새롭게 추가된 바우처를 넣고 이 아이디로 검색한 결과, 새롭게 추가된 바우처와 검색한 결과의 바우처는 같다.")
    void findById_InsertVoucher_EqualsReturnVoucher() {
        //when
        Optional<Voucher> result = voucherRepository.findById(FIX_VOUCHER_ID);

        //then
        assertThat(result.get()).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(fixVoucher);
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
                VoucherType.FIXED_AMOUNT_VOUCHER, LocalDateTime.now());

        //when
        Voucher result = voucherRepository.insert(voucher);

        //then
        assertThat(result).isNotNull()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("모든 바우처를 조회했을 때 추가한 바우처의 목록과 같다.")
    void getAllVoucher_AllVouchers_SameContents() {
        //when
        Vouchers result = voucherRepository.getAllVoucher(null, null);

        //then
        assertThat(result.vouchers())
                .isNotNull()
                .contains(fixVoucher,percemtVoucher);
    }


    @Test
    @DisplayName("필터에 바우처 타입으로 고정된 바우처를 지정했을 때 고정된 바우처의 결과만을 반환한다.")
    void findAll_FilterWithFixedVoucherType_FixedVoucher() {
        //when
        Vouchers result = voucherRepository.getAllVoucher(VoucherType.FIXED_AMOUNT_VOUCHER, null);

        //then
        assertThat(result.vouchers())
                .isNotNull()
                .contains(fixVoucher);
    }


    @Test
    @DisplayName("필터에 바우처 타입으로 할인율 바우처를 지정했을 때 할인율 바우처의 결과만을 반환한다.")
    void findAll_FilterWithPercentVoucherType_FixedVoucher() {
        //when
        Vouchers result = voucherRepository.getAllVoucher(VoucherType.PERCENT_DISCOUNT_VOUCHER, null);

        //then
        assertThat(result.vouchers())
                .isNotNull()
                .contains(percemtVoucher);
    }


    @Test
    @DisplayName("필터에 날짜 조건만 어제 날짜로 설졍한 경우 어제 날짜 이후로 만들어진 바우처를 반환한다.")
    void findAll_FilterWithYesterday_LaterThanYesterday() {
        //when
        Vouchers result = voucherRepository.getAllVoucher(null, yesterday);

        //then
        assertThat(result.vouchers()).hasSize(2);
    }

    @Test
    @DisplayName("필터에 날짜 조건만 내일 날짜로 설졍한 경우 어떤 바우처도 반환하지 않는다.")
    void findAll_FilterWithTomorrow_Empty() {
        //when
        Vouchers result = voucherRepository.getAllVoucher(null, tomorrow);

        //then
        assertThat(result.vouchers()).hasSize(0);
    }

}
