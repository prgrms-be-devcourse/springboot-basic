package com.programmers.voucher.domain.voucher.service;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static com.programmers.voucher.testutil.VoucherTestUtil.createPercentVoucher;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @ParameterizedTest
    @CsvSource({
            "1", "10", "100"
    })
    @DisplayName("성공: FixedAmountVoucher 생성")
    void createFixedAmountVoucher(long amount) {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;

        //when
        voucherService.createVoucher(voucherType, amount);

        //then
        then(voucherRepository).should().save(any());
    }

    @ParameterizedTest
    @CsvSource({
            "-10", "-1", "0"
    })
    @DisplayName("예외: FixedAmountVoucher 생성 - 잘못된 할인값")
    void createFixedAmountVoucher_ButInvalidAmount_Then_Exception(long amount) {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT;

        //when
        //then
        assertThatThrownBy(() -> voucherService.createVoucher(voucherType, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "50", "99"
    })
    @DisplayName("성공: PercentDiscountVoucher 생성")
    void createPercentDiscountVoucher() {
        //given
        VoucherType voucherType = VoucherType.PERCENT;
        long percent = 10;

        //when
        voucherService.createVoucher(voucherType, percent);

        //then
        then(voucherRepository).should().save(any());
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "0", "100", "101"
    })
    @DisplayName("예외: PercentDiscountVoucher 생성 - 잘못된 할인률")
    void createPercentDiscountVoucher_ButInvalidPercent_Then_Exception(long percent) {
        //given
        VoucherType voucherType = VoucherType.PERCENT;

        //when
        //then
        assertThatThrownBy(() -> voucherService.createVoucher(voucherType, percent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("성공: Voucher 목록 조회")
    void findVouchers() {
        //given
        Voucher fixedVoucher = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher percentVoucher = createPercentVoucher(UUID.randomUUID(), 10);

        List<Voucher> store = List.of(fixedVoucher, percentVoucher);

        given(voucherRepository.findAll()).willReturn(store);

        //when
        List<Voucher> result = voucherService.findVouchers();

        //then
        assertThat(result).contains(fixedVoucher, percentVoucher);
    }
}