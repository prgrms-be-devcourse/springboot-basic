package org.prgrms.kdt.voucher.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.NotFoundException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@ActiveProfiles("dev")
class VoucherServiceTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order"})
    static class Config {
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @Test
    @DisplayName("ApplicationContext가 생성 되어야 한다.")
    public void testApplicationContext() {
        assertThat(context, notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation() {
        VoucherRepository bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }


    @Test
    @DisplayName("[성공] VoucherId를가지고 Voucher List에서 Voucher 찾기")
    void findByVoucher_SUCCESS() {
        // given
        UUID uuid = UUID.randomUUID();
        Voucher actual = new FixedAmountVoucher(uuid, 10);
        voucherRepository.insert(actual);

        // when
        Voucher expected = voucherService.findByVoucher(uuid);

        // then
        assertThat(actual, is(expected));

    }

    @Test
    @DisplayName("[실패] VoucherId를가지고 Voucher List에서 Voucher 찾기")
    void findByVoucher_FAIL() {
        // given
        UUID uuid = UUID.randomUUID();
        Voucher actual = new FixedAmountVoucher(uuid, 10);
        voucherRepository.insert(actual);

        // then
        assertThrows(NotFoundException.class, () -> voucherService.findByVoucher(UUID.randomUUID()));
    }

    @Test
    @DisplayName("[성공] voucher를 voucherRepository에 등록시키기")
    void insertVoucher_SUCCESS() {
        //given
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(uuid, 10);

        //then
        voucherService.insert(voucher);
        int voucherRepositorySize = voucherRepository.size();

        //when
        assertThat(voucherRepositorySize, is(1));
    }

    @Test
    @DisplayName("[실패] voucher를 voucherRepository에 등록시키기")
    void insertVoucher_FAIL() {
        //given
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(uuid1, 10);
        Voucher voucher2 = new FixedAmountVoucher(uuid2, 11);

        //then
        voucherService.insert(voucher1);
        voucherService.insert(voucher2);
        int voucherRepositorySize = voucherRepository.size();

        //when
        assertThat(voucherRepositorySize, is(1));
    }

    @Test
    @DisplayName("[성공] 모든 바우처 정보 출력시키기")
    void allVoucherPrint_SUCCESS() {
        //given
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(uuid1, 10);
        Voucher voucher2 = new FixedAmountVoucher(uuid2, 11);
        voucherService.insert(voucher1);
        voucherService.insert(voucher2);

        //then
        Map<UUID, Voucher> actual = voucherService.findByAllVoucher();

        //when
        assertThat(2, equalTo(actual.size()));

    }

    @Test
    @DisplayName("[성공] Voucher 생성기")
    void createVoucher_SUCCESS() {
        //given
        VoucherType fixedType = VoucherType.FIXED;
        VoucherType percentType = VoucherType.PERCENT;
        String amount = "100";
        String percent = "10";

        //then
        Voucher fixedVoucher_expected = voucherService.createVoucher(fixedType, amount);
        Voucher percentVoucher_expected = voucherService.createVoucher(percentType, percent);

        Voucher fixedVoucher_actual = new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(amount));
        Voucher percentVoucher_actual = new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(percent));

        //when
        assertEquals(fixedVoucher_actual.value(), fixedVoucher_expected.value());
        assertEquals(percentVoucher_actual.value(), percentVoucher_expected.value());

    }
}