package org.prgrms.kdt.engine.voucher.service;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.prgrms.kdt.engine.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.engine.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.engine.voucher"})
    static class Config {
        @Bean
        public VoucherRepository voucherRepository() {
            return new MemoryVoucherRepository();
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    Voucher newFixedVoucher;
    Voucher newPercentVoucher;

    @Test
    @Order(1)
    @DisplayName("바우처를 생성할 수 있다")
    void testCreateVoucher() {
        newFixedVoucher = voucherService.createVoucher(VoucherType.FIXED, 10);
        newPercentVoucher = voucherService.createVoucher(VoucherType.PERCENT, 15);

        var maybeNewFixedVoucher = voucherService.getVoucher(newFixedVoucher.getVoucherId());
        var maybeNewPercentVoucher = voucherService.getVoucher(newPercentVoucher.getVoucherId());

        assertThat(newFixedVoucher, samePropertyValuesAs(maybeNewFixedVoucher));
        assertThat(newPercentVoucher, samePropertyValuesAs(maybeNewPercentVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 개별 조회할 수 있다")
    void testGetVoucher() {
        var maybeNewVoucher = voucherService.getVoucher(newFixedVoucher.getVoucherId());
        assertThat(maybeNewVoucher, samePropertyValuesAs(newFixedVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("유효한 바우처만 조회할 수 있다")
    void testGetInvalidVoucher() {
        assertThrows(RuntimeException.class, () -> voucherService.getVoucher(UUID.randomUUID()));
    }

    @Test
    @Order(4)
    @DisplayName("모든 바우처를 조회할 수 있다")
    void testListVoucher() {
        var vouchers = voucherService.listVoucher();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.get().size(), is(2));
    }
}
