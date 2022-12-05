package org.prgrms.java.repository.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class MemoryVoucherRepositoryTest {
    @Configuration
    static class Config {
        @Bean
        VoucherRepository voucherRepository() {
            return new MemoryVoucherRepository();
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처를 등록할 수 있다.")
    void testInsert() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        Voucher insertedFixedAmountVoucher = voucherRepository.insert(fixedAmountVoucher);

        assertThat(fixedAmountVoucher, samePropertyValuesAs(insertedFixedAmountVoucher));
    }

    @Test
    @DisplayName("동일한 ID의 바우처는 등록할 수 없다.")
    void testInsertSameIdVoucher() {
        assertThrows(VoucherException.class, () -> {
            UUID voucherId = UUID.randomUUID();
            Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 100);
            Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, 10);

            voucherRepository.insert(fixedAmountVoucher);
            voucherRepository.insert(percentDiscountVoucher);
        });
    }

    @Test
    @DisplayName("등록한 바우처가 정상적으로 반환돼야 한다.")
    void testFindById() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get(), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(voucherRepository.findById(percentDiscountVoucher.getVoucherId()).get(), samePropertyValuesAs(percentDiscountVoucher));
        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get(), not(samePropertyValuesAs((percentDiscountVoucher))));
    }

    @Test
    @DisplayName("등록한 바우처와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 100);

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findAll().isEmpty(), is(false));
        assertThat(voucherRepository.findAll(), hasSize(2));
    }
}