package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedDiscount;
import com.programmers.voucher.domain.PercentDiscount;
import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@JdbcTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcVoucherRepositoryTest {

    @TestConfiguration
    @ComponentScan("com.programmers.voucher")
    static class Config{
        @Bean
        public JdbcVoucherRepository jdbcVoucherRepository(DataSource dataSource) {
            return new JdbcVoucherRepository(dataSource);
        }
    }

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @DisplayName("DB에 바우처 정보를 저장할 수 있다.")
    @Test
    void insertVoucherTest() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());

        Voucher storedVoucher = jdbcVoucherRepository.save(testVoucher);
        
        assertThat(testVoucher.getVoucherId()).isEqualTo(storedVoucher.getVoucherId());
    }

    @DisplayName("ID로 바우처를 조회할 수 있다.")
    @Test
    void findVoucherByIdTest() {
        UUID id = UUID.randomUUID();
        Voucher testVoucher = new Voucher(id, new FixedDiscount(100), LocalDateTime.now());
        jdbcVoucherRepository.save(testVoucher);

        Voucher storedVoucher = jdbcVoucherRepository.findById(id);

        assertThat(storedVoucher.getVoucherId()).isEqualTo(id);
    }

    @DisplayName("DB에서 모든 바우처를 조회할 수 있다.")
    @Test
    void findAllTest() {
        for (int i = 0; i < 3; i++) {
            Voucher testVoucher = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());
            jdbcVoucherRepository.save(testVoucher);
        }

        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers.size()).isEqualTo(3);
    }

    @DisplayName("할인 타입으로 바우처를 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"FIXED", "PERCENT"})
    void findVouchersByTypeTest(String type) {
        int fixedCount = 0;
        int percentCount = 0;
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                Voucher testVoucher = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());
                jdbcVoucherRepository.save(testVoucher);
                fixedCount++;
                continue;
            }
            Voucher testVoucher = new Voucher(UUID.randomUUID(), new PercentDiscount(100), LocalDateTime.now());
            jdbcVoucherRepository.save(testVoucher);
            percentCount++;
        }

        List<Voucher> vouchers = jdbcVoucherRepository.findByType(type);

        if (type == "FIXED") assertThat(vouchers.size()).isEqualTo(fixedCount);
        else assertThat(vouchers.size()).isEqualTo(percentCount);
    }

    @DisplayName("ID로 바우처를 삭제할 수 있다.")
    @Test
    void deleteVoucherByIdTest() {
        UUID id = UUID.randomUUID();
        Voucher testVoucher = new Voucher(id, new FixedDiscount(100), LocalDateTime.now());
        jdbcVoucherRepository.save(testVoucher);

        jdbcVoucherRepository.deleteById(id);

        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.size()).isEqualTo(0);
    }
}
