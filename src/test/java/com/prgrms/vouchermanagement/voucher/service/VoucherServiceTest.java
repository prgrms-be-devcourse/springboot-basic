package com.prgrms.vouchermanagement.voucher.service;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.repository.JdbcVoucherRepository;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static com.prgrms.vouchermanagement.voucher.VoucherType.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class VoucherServiceTest {

    @Configuration
    @ComponentScan
    static class TestConfig {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("voucher_schema.sql")
                    .setScriptEncoding("UTF-8")
                    .build();
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        JdbcVoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }

        @Bean
        VoucherService voucherService(VoucherRepository voucherRepository) {
            return new VoucherService(voucherRepository);
        }
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @AfterEach
    void afterEach() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("Voucher를 저장한다.")
    void addVoucherTest() {
        // given
        VoucherType fixedDiscount = FIXED_DISCOUNT;
        long amount = 50000;

        // when
        Long voucherId = voucherService.addVoucher(fixedDiscount, amount);

        // then
        Voucher findVoucher = voucherRepository.findById(voucherId).get();
        assertThat(getVoucherType(findVoucher)).isEqualTo(FIXED_DISCOUNT);
        assertThat(findVoucher.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("모든 Voucher를 조회한다.")
    void findAllVouchesTest() {
        // given
        Voucher voucher1 = FIXED_DISCOUNT.constructor(5000, LocalDateTime.now());
        Voucher voucher2 = PERCENT_DISCOUNT.constructor(15, LocalDateTime.now());
        Voucher voucher3 = FIXED_DISCOUNT.constructor(120000, LocalDateTime.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // when
        List<Voucher> vouchers = voucherService.findAllVouchers();

        // then
        assertThat(vouchers.size()).isEqualTo(3);
        assertThat(vouchers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("voucherId").contains(voucher1, voucher2, voucher3);
    }

    @Test
    @DisplayName("voucherId를 전달 받아 등록된 Voucher인지 확인한다")
    void isRegisteredVoucherTest() {
        // given
        Long voucherId = voucherService.addVoucher(FIXED_DISCOUNT, 120000);

        // when
        boolean registeredVoucher = voucherService.isRegisteredVoucher(voucherId);

        // then
        assertThat(registeredVoucher).isTrue();
    }

    @Test
    @DisplayName("등록되지 않은 voucherId로 isRegisteredVoucher() 호출하면 false가 반환된다.")
    void isRegisteredVoucherNotExistsIdTest() {
        // given
        Long wrongVoucherId = -1L;

        // when
        boolean registeredVoucher = voucherService.isRegisteredVoucher(wrongVoucherId);

        // then
        assertThat(registeredVoucher).isFalse();
    }
}