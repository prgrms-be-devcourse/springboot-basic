package org.prgrms.kdt.voucher.service;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher.repository", "org.prgrms.kdt.voucher.service"}
    )
    static class Config{
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    FixedAmountVoucher newfixedAmountVoucher;
    PercentDiscountVoucher newPercentDiscountVoucher;

    @BeforeAll
    void setUp(){
        newfixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        newPercentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
    }

    @AfterEach()
    void afterEach(){
        voucherRepository.clearAllVouchers();
    }

    @Test
    @DisplayName("입력된 값을 이용하여 Voucher를 생성한다")
    void testCreateVoucher() {
        //given
        //when
        Voucher fixedAmountVoucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT, newfixedAmountVoucher.getVoucherId(), newfixedAmountVoucher.getAmount());
        Voucher percentDiscountVoucher = voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT, newPercentDiscountVoucher.getVoucherId(), newPercentDiscountVoucher.getPercent());

        //then
        assertAll( "Creating FiexdAmount Voucher",
                () -> assertThat(fixedAmountVoucher, notNullValue()),
                () -> assertThat(fixedAmountVoucher.getClass(), is(FixedAmountVoucher.class)),
                () -> assertThat(fixedAmountVoucher, samePropertyValuesAs(newfixedAmountVoucher))
        );

        assertAll( "Creating PercentDiscount Voucher",
                () -> assertThat(percentDiscountVoucher, notNullValue()),
                () -> assertThat(percentDiscountVoucher.getClass(), is(PercentDiscountVoucher.class)),
                () -> assertThat(percentDiscountVoucher, samePropertyValuesAs(newPercentDiscountVoucher))
        );
    }

    @Test
    @DisplayName("입력된 Voucher를 Repository에 추가한다.")
    void testAddVoucher() {
        //given
        //when
        voucherService.addVoucher(newfixedAmountVoucher);
        voucherService.addVoucher(newPercentDiscountVoucher);

        //then
        assertThat(voucherService.getAllVouchers().size(), is(2));
    }

    @Test
    @DisplayName("id에 해당하는 Voucher를 반환한다.")
    void testGetVoucher() {
        //given
        voucherService.addVoucher(newfixedAmountVoucher);
        voucherService.addVoucher(newPercentDiscountVoucher);

        //when
        Voucher fixedVoucher = voucherService.getVoucher(newfixedAmountVoucher.getVoucherId());
        Voucher percentVoucher = voucherService.getVoucher(newPercentDiscountVoucher.getVoucherId());

        //then
        assertThat(fixedVoucher, samePropertyValuesAs(newfixedAmountVoucher));
        assertThat(percentVoucher, samePropertyValuesAs(newPercentDiscountVoucher));
    }

    @Test
    @DisplayName("모든 Voucher를 반환한다.")
    void testGetAllVouchers() {
        //given
        voucherService.addVoucher(newfixedAmountVoucher);
        voucherService.addVoucher(newPercentDiscountVoucher);

        //when
        List<Voucher> allVouchers = voucherService.getAllVouchers();

        //then
        assertThat(allVouchers.size(), is(2));
        assertThat(allVouchers, containsInAnyOrder(newfixedAmountVoucher, newPercentDiscountVoucher));
    }

    @Test
    @DisplayName("모든 Voucher를 삭제한다.")
    void clearAllVouchers() {
        //given
        voucherService.addVoucher(newfixedAmountVoucher);
        voucherService.addVoucher(newPercentDiscountVoucher);

        //when
        voucherService.clearAllVouchers();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));
    }
}