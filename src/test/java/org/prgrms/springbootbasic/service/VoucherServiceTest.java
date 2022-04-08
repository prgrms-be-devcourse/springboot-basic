package org.prgrms.springbootbasic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.repository.MemoryVoucherRepository;

class VoucherServiceTest {

    VoucherService voucherService;
    MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    void init() {
        memoryVoucherRepository = new MemoryVoucherRepository();
        voucherService = new VoucherService(memoryVoucherRepository);
    }

    @DisplayName("FixedAmountVoucher 만들기 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        long amount = 10L;

        //when
        voucherService.createFixedAmountVoucher(amount);

        //then
        assertThat(memoryVoucherRepository.getVoucherTotalNumber())
            .isEqualTo(1);
    }

    @DisplayName("PercentAmountVoucher 만들기 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        int percent = 10;

        //when
        voucherService.createPercentAmountVoucher(percent);

        //then
        assertThat(memoryVoucherRepository.getVoucherTotalNumber())
            .isEqualTo(1);
    }

    @DisplayName("Voucher 전체 조회 테스트")
    @Test
    void findAll() {
        //given
        voucherService.createFixedAmountVoucher(10L);
        voucherService.createPercentAmountVoucher(10);

        //when
        List<Voucher> vouchers = voucherService.findAll();

        //then
        assertThat(vouchers.size())
            .isEqualTo(2);
    }
}