package org.prgms.kdtspringweek1.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prgms.kdtspringweek1.controller.dto.voucherDto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig
class VoucherServiceTest {

    @Configuration
    static class Config {
        @Bean
        public VoucherService voucherService() {
            voucherRepository = Mockito.mock(VoucherRepository.class);
            return new VoucherService(voucherRepository);
        }
    }

    private static VoucherRepository voucherRepository;
    @Autowired
    VoucherService voucherService;

    @Test
    @DisplayName("바우처 등록 성공")
    void Success_RegisterVoucher() {
        // given
        Voucher voucher = FixedAmountVoucher.createWithAmount(100);
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        // when
        Voucher registeredVoucher = voucherService.registerVoucher(voucher);

        // then
        assertThat(registeredVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("모든 바우처 조회 성공")
    void Success_SearchAllVouchers() {
        // given
        when(voucherRepository.findAllVouchers()).thenReturn(createVouchers());

        // when
        List<FindVoucherResponseDto> vouchers = voucherService.searchAllVouchers();

        // then
        assertThat(vouchers, hasSize(14));
    }

    private List<Voucher> createVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            vouchers.add(FixedAmountVoucher.createWithAmount(7));
            vouchers.add(PercentDiscountVoucher.createWithPercent(7));
        }

        return vouchers;
    }
}