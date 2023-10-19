package org.prgrms.kdtspringdemo.voucher.repository;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100, "fixedAmount");

        //when
        Voucher insertVoucher = memoryVoucherRepository.insert(voucher);

        //then
        assertThat(voucher, samePropertyValuesAs(insertVoucher));
    }

    @Test
    @DisplayName("voucherId로 바우처 검색")
    void findById() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 20, "percentDiscount");
        memoryVoucherRepository.insert(voucher);

        //when
        Voucher findVoucher = memoryVoucherRepository.findById(voucher.getVoucherId()).get();

        //then
        assertThat(voucher, samePropertyValuesAs(findVoucher));
    }

    @Test
    @DisplayName("모든 바우처 조회")
    void getAllVouchers() {
        //given
        memoryVoucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 100, "fixedAmount"));
        memoryVoucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 2000, "fixedAmount"));
        memoryVoucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 5, "percentDiscount"));

        //when
        Map<UUID, Voucher> allVouchers = memoryVoucherRepository.getAllVouchers().get();

        //then
        assertThat(allVouchers.size(), is(3));
    }
}