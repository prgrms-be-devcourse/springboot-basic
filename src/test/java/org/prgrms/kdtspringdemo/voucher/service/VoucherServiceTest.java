package org.prgrms.kdtspringdemo.voucher.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.repository.FileVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("DB")
class VoucherServiceTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }

    @Autowired
    private VoucherService voucherService;
    private final String filePath = "src/test/resources/test_voucherList";
    private final Logger logger = LoggerFactory.getLogger(VoucherServiceTest.class);

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
        Voucher createdVoucher = voucherService.createVoucher(voucherType, amount);

        //then
        //assertThat(createdVoucher.getVoucherId(), is(voucherId));
        assertThat(createdVoucher.getVoucherPolicy(), instanceOf(PercentDiscountPolicy.class));
    }

    @Test
    @DisplayName("바우처 리스트를 반환합니다.")
    void getVoucherList() {
        //given
        voucherService.createVoucher(VoucherTypeFunction.PERCENT_DISCOUNT_POLICY,20L);
        voucherService.createVoucher(VoucherTypeFunction.FIXED_DISCOUNT_POLICY, 400L);

        //when
        List<Voucher> voucherList = voucherService.findAll();

        //then
        assertThat(voucherList.size(), is(2));
    }

    @Test
    @DisplayName("voucherId 로 바우처를 반환합니다.")
    void getVoucher() {
        //given
        Voucher createdVoucher = voucherService.createVoucher(VoucherTypeFunction.FIXED_DISCOUNT_POLICY, 2000L);

        //when
        Voucher voucher = voucherService.findById(createdVoucher.getVoucherId());

        //then
        assertThat(createdVoucher.getVoucherId(), is(voucher.getVoucherId()));
        assertThat(createdVoucher.getVoucherPolicy().getClass(), sameInstance(voucher.getVoucherPolicy().getClass()));
    }
}