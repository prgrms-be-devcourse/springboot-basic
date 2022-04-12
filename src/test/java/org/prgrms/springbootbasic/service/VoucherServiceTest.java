package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.springbootbasic.repository.voucher.MemoryVoucherRepository;

class VoucherServiceTest {

    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
    VoucherService voucherService = new VoucherService(memoryVoucherRepository);

    @BeforeEach
    void init() {
        memoryVoucherRepository.removeAll();
    }

    @DisplayName("FixedAmountVoucher 만들기 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        long amount = 10L;

        //when
        voucherService.createFixedAmountVoucher(amount);

        //then
        assertThat(memoryVoucherRepository.findAll().size())
            .isEqualTo(1);
    }

    @DisplayName("FixedAmountVoucher 만들기 실패")
    @ParameterizedTest
    @ValueSource(longs = {0, -1, 100000})
    void createFixedAmountVoucherFail(long amount) {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherService.createFixedAmountVoucher(amount))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("PercentAmountVoucher 만들기 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        int percent = 10;

        //when
        voucherService.createPercentAmountVoucher(percent);

        //then
        assertThat(memoryVoucherRepository.findAll().size())
            .isEqualTo(1);
    }

    @DisplayName("PercentAmountVoucher 만들기 실패")
    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void createPercentAmountVoucherFail(int percent) {
        //given
        //when
        //then
        assertThatThrownBy(() -> voucherService.createPercentAmountVoucher(percent))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Voucher 전체 조회 테스트")
    @Test
    void findAll() {
        //given
        voucherService.createFixedAmountVoucher(10L);
        voucherService.createPercentAmountVoucher(10);

        //when
        //then
        assertThat(voucherService.findAll().size())
            .isEqualTo(2);
    }
}
