package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class JdbcVoucherRepositoryTest {

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
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @AfterEach
    void afterEach() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("Voucher를 저장한다.")
    void saveTest() {
        // given
        Voucher percentVoucher =  VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());
        Voucher fixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(50000, LocalDateTime.now());

        // when
        Long percentVoucherId = voucherRepository.save(percentVoucher);
        Long fixedVoucherId = voucherRepository.save(fixedVoucher);

        // then
        Voucher findPercentVoucher = voucherRepository.findById(percentVoucherId).get();
        assertThat(findPercentVoucher).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(percentVoucher);

        Voucher findFixedVoucher = voucherRepository.findById(fixedVoucherId).get();
        assertThat(findFixedVoucher).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("voucherId로 Voucher를 조회한다.")
    void findByIdTest() {
        // given
        Voucher percentVoucher =  VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());
        Voucher fixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(50000, LocalDateTime.now());
        Long percentVoucherId = voucherRepository.save(percentVoucher);
        Long fixedVoucherId = voucherRepository.save(fixedVoucher);

        // when
        Voucher findPercentVoucher = voucherRepository.findById(percentVoucherId).get();
        Voucher findFixedVoucher = voucherRepository.findById(fixedVoucherId).get();


        // then
        assertThat(findPercentVoucher).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(percentVoucher);
        assertThat(findFixedVoucher).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(fixedVoucher);
    }

    @Test
    @DisplayName("존재하지 않는 voucherId로 Voucher를 조회하면 Optional.empty()를 반환한다.")
    void findByNotExistsIdTest() {
        // given
        Voucher percentVoucher =  VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());
        Voucher fixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(50000, LocalDateTime.now());
        voucherRepository.save(percentVoucher);
        voucherRepository.save(fixedVoucher);
        Long wrongVoucherId = -1L;

        // when
        Optional<Voucher> findVoucher = voucherRepository.findById(wrongVoucherId);

        // then
        assertThat(findVoucher).isEmpty();
    }

    @Test
    @DisplayName("Voucher를 update한다.")
    void updateTest() {
        // given
        Voucher percentVoucher =  VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());
        Voucher fixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(50000, LocalDateTime.now());
        Long percentVoucherId = voucherRepository.save(percentVoucher);
        Long fixedVoucherId = voucherRepository.save(fixedVoucher);

        // when
        Voucher updatePercentVoucher = VoucherType.PERCENT_DISCOUNT.constructor(percentVoucherId, 70, percentVoucher.getCreatedAt());
        Voucher updateFixedVoucher = VoucherType.FIXED_DISCOUNT.constructor(fixedVoucherId, 70000, fixedVoucher.getCreatedAt());

        voucherRepository.update(updatePercentVoucher);
        voucherRepository.update(updateFixedVoucher);

        // then
        Voucher findPercentVoucher = voucherRepository.findById(percentVoucherId).get();
        Voucher findFixedVoucher = voucherRepository.findById(fixedVoucherId).get();

        assertThat(updatePercentVoucher).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(findPercentVoucher);
        assertThat(updateFixedVoucher).usingRecursiveComparison().ignoringFields("voucherId").isEqualTo(findFixedVoucher);
    }

    @Test
    @DisplayName("Voucher를 삭제한다.")
    void removeTest() {
        // given
        Voucher voucher = VoucherType.FIXED_DISCOUNT.constructor(50, LocalDateTime.now());
        Long voucherId = voucherRepository.save(voucher);

        // when
        voucherRepository.remove(voucherId);

        // then
        assertThat(voucherRepository.findById(voucherId)).isEmpty();
    }

    @Test
    @DisplayName("전체 Voucher를 조회한다.")
    void findAllTest() {
        // given
        Voucher voucher1 =  VoucherType.PERCENT_DISCOUNT.constructor(50, LocalDateTime.now());
        Voucher voucher2 = VoucherType.FIXED_DISCOUNT.constructor(50000, LocalDateTime.now());
        Voucher voucher3 = VoucherType.PERCENT_DISCOUNT.constructor(10, LocalDateTime.now());
        Voucher voucher4 = VoucherType.FIXED_DISCOUNT.constructor(3000, LocalDateTime.now());
        Voucher voucher5 = VoucherType.PERCENT_DISCOUNT.constructor(30, LocalDateTime.now());

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);
        voucherRepository.save(voucher5);

        // when
        List<Voucher> allVouchers = voucherRepository.findAll();

        // then
        assertThat(allVouchers.size()).isEqualTo(5);
        assertThat(allVouchers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("voucherId").contains(voucher1, voucher2, voucher3, voucher4, voucher5);
    }

    @Test
    @DisplayName("전체 Voucher를 조회하는데 저장된 Voucher가 없는 경우 빈 리스트를 반환한다.")
    void findAllEmptyTest() {
        // given
        // 아무 바우처도 저장하지 않는다.

        // when
        List<Voucher> allVouchers = voucherRepository.findAll();

        // then
        assertThat(allVouchers).isNotNull();
        assertThat(allVouchers).isEmpty();
    }
}