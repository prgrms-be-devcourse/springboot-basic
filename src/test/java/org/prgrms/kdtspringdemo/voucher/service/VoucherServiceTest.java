package org.prgrms.kdtspringdemo.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ActiveProfiles("DB")
class VoucherServiceTest {

    private final String filePath = "src/test/resources/test_voucherList";
    private final Logger logger = LoggerFactory.getLogger(VoucherServiceTest.class);
    @Autowired
    private VoucherService voucherService;

    @BeforeEach
    void init() {
        voucherService.deleteAll();
    }

    @Test
    @DisplayName("바우처 타입 이넘을 반환한다.")
    void getVoucherTypeFunction() {
        //given
        String voucherType = "fixeddiscount";

        //when
        VoucherTypeFunction voucherTypeFunction = voucherService.getVoucherTypeFunction(voucherType);

        //then
        assertThat(voucherTypeFunction.name(), is("FIXED_DISCOUNT_POLICY"));
    }

    @Test
    @DisplayName("잘못된 바우처 타입이 입력되면 NoSuchElementException")
    void getVoucherTypeFunctionError() {
        //given
        String voucherType = "noneVoucher";

        //then
        assertThrows(NoSuchElementException.class, () -> voucherService.getVoucherTypeFunction(voucherType));
    }

    @Test
    @DisplayName("바우처를 생성합니다")
    void createVoucher() {
        //given
        VoucherTypeFunction voucherType = VoucherTypeFunction.PERCENT_DISCOUNT_POLICY;
        long amount = 20L;

        //when
        VoucherViewDto createdVoucher = voucherService.createVoucher(voucherType, amount);

        //then
        //assertThat(createdVoucher.getVoucherId(), is(voucherId));
        assertThat(createdVoucher.getVoucherPolicy(), instanceOf(PercentDiscountPolicy.class));
    }

    @Test
    @DisplayName("바우처 리스트를 반환합니다.")
    void getVoucherList() {
        //given
        voucherService.createVoucher(VoucherTypeFunction.PERCENT_DISCOUNT_POLICY, 20L);
        voucherService.createVoucher(VoucherTypeFunction.FIXED_DISCOUNT_POLICY, 400L);

        //when
        List<VoucherViewDto> voucherList = voucherService.getVoucherViewDtoList();

        //then
        assertThat(voucherList.size(), is(2));
    }

    @Test
    @DisplayName("voucherId 로 바우처를 반환합니다.")
    void getVoucher() {
        //given
        VoucherViewDto createdVoucher = voucherService.createVoucher(VoucherTypeFunction.FIXED_DISCOUNT_POLICY, 2000L);

        //when
        Voucher voucher = voucherService.findById(createdVoucher.getVoucherId());

        //then
        assertThat(createdVoucher.getVoucherId(), is(voucher.getVoucherId()));
        assertThat(createdVoucher.getVoucherPolicy().getClass(), sameInstance(voucher.getVoucherPolicy().getClass()));
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }
}