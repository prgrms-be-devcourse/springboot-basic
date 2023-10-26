package org.prgrms.kdtspringdemo.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.domain.FixedDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("local")
class MemoryVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }
    @Autowired
    MemoryVoucherRepository memoryVoucherRepository;

    @Test
    @DisplayName("바우처 등록")
    void insert() {
        //given
        VoucherPolicy fixedDiscountPolicy = new FixedDiscountPolicy(100);
        Voucher fixedDiscountVoucher = new Voucher(UUID.randomUUID(), fixedDiscountPolicy);
        VoucherPolicy percentDiscountPolicy = new PercentDiscountPolicy(25);
        Voucher percentDiscountVoucher = new Voucher(UUID.randomUUID(), percentDiscountPolicy);

        //when
        Voucher insertFixedDiscountVoucher = memoryVoucherRepository.insert(fixedDiscountVoucher);
        Voucher insertPercentDiscountVoucher = memoryVoucherRepository.insert(percentDiscountVoucher);

        //then
        assertThat(fixedDiscountVoucher.getVoucherId(), is(insertFixedDiscountVoucher.getVoucherId()));
        assertThat(percentDiscountVoucher.getVoucherId(), is(insertPercentDiscountVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 등록 실패")
    void insertError() {
        try {
            //given
            VoucherPolicy percentDiscountVoucherPolicy = new PercentDiscountPolicy(200);
            Voucher voucher = new Voucher(UUID.randomUUID(), percentDiscountVoucherPolicy);

            //when
            Voucher insertPercentDiscountVoucher = memoryVoucherRepository.insert(voucher);

        } catch (RuntimeException e) {
            //then
            assertThat(e, instanceOf(RuntimeException.class));
        }
    }

    @Test
    @DisplayName("voucherId로 바우처 검색")
    void findById() {
        //given
        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(20);
        Voucher inserVoucher = new Voucher(UUID.randomUUID(), voucherPolicy);
        memoryVoucherRepository.insert(inserVoucher);

        //when
        Voucher findVoucher = memoryVoucherRepository.findById(inserVoucher.getVoucherId()).get();

        //then
        assertThat(findVoucher.getVoucherId(), is(findVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("모든 바우처 조회")
    void getAllVouchers() {
        //given
        memoryVoucherRepository.insert(new Voucher(UUID.randomUUID(), new FixedDiscountPolicy(100)));
        memoryVoucherRepository.insert(new Voucher(UUID.randomUUID(), new FixedDiscountPolicy(2000)));
        memoryVoucherRepository.insert(new Voucher(UUID.randomUUID(), new PercentDiscountPolicy(5)));

        //when
        List<Voucher> allVouchers = memoryVoucherRepository.findAll().get();

        //then
        assertThat(allVouchers.size(), is(3));
    }
}