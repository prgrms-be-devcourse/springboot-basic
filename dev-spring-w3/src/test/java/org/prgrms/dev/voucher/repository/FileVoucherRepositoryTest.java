package org.prgrms.dev.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@ActiveProfiles("file")
class FileVoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @Order(1)
    @DisplayName("파일에 바우처를 저장할 수 있다.")
    void create() {
        UUID voucherId = UUID.randomUUID();
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, 30);

        voucherRepository.create(percentDiscountVoucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);
        assertThat(retrievedVoucher.get(), is(percentDiscountVoucher));
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(percentDiscountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 아이디로 원하는 바우처를 조회할 수 있다.")
    void findById() {
        UUID voucherId = UUID.fromString("8255af63-cdd9-4dfd-9312-096e04452606");

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getVoucherId(), is(voucherId));
    }

    @Test
    @Order(3)
    @DisplayName("파일에 저장되어있는 모든 바우처 조회할 수 있다.")
    void findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers.isEmpty(), is(false));
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.dev.voucher"}
    )
    static class Config {
    }
}