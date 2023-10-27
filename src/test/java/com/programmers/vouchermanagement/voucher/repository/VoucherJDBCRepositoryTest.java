package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJDBCRepositoryTest {
    @Autowired
    VoucherJDBCRepository voucherJDBCRepository;
    @Autowired
    DataSource dataSource;

    @Test
    @Order(1)
    @DisplayName("HikariConnectionPool 연결할 수 있다.")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("고정 금액 할인 바우처를 추가할 수 있다.")
    void saveFixedAmountVoucherSucceed() {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(555), VoucherType.FIXED);
        voucherJDBCRepository.save(newVoucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(newVoucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getVoucherId()).isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @Order(3)
    @DisplayName("퍼센트 할인 바우처를 추가할 수 있다.")
    void savePercentVoucherSucceed() {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT);
        voucherJDBCRepository.save(newVoucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(newVoucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getVoucherId()).isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @Order(4)
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAllVoucherSucceed() {
        List<Voucher> vouchers = voucherJDBCRepository.findAll();

        assertThat(vouchers.isEmpty()).isFalse();
    }

    @Test
    @Order(5)
    @DisplayName("바우처를 아이디로 조회할 수 있다.")
    void findVoucherByIdSucceed() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(1234), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent()).isTrue();
        assertThat(retrievedVoucher.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
        assertThat(retrievedVoucher.get().getVoucherType()).isEqualTo(voucher.getVoucherType());
    }

    @Test
    @Order(6)
    @DisplayName("바우처를 아이디로 삭제할 수 있다.")
    void deleteVoucherSucceed() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(5555), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        voucherJDBCRepository.delete(voucher.getVoucherId());

        assertThat(voucherJDBCRepository.findById(voucher.getVoucherId()).isEmpty()).isTrue();
    }

    @Test
    @Order(7)
    @DisplayName("없는 바우처를 삭제하면 실패한다.")
    void deleteNonExistVoucherFail() {
        UUID NonExistVoucherId = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> voucherJDBCRepository.delete(NonExistVoucherId));
    }

    @Test
    @Order(8)
    @DisplayName("바우처를 업데이트 할 수 있다.")
    void updateVoucherSucceed() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(5555), VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        Voucher updatedVoucher = new Voucher(voucher.getVoucherId(), BigDecimal.valueOf(100), VoucherType.PERCENT);
        voucherJDBCRepository.update(updatedVoucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(updatedVoucher.getDiscountValue());
        assertThat(retrievedVoucher.get().getVoucherType()).isEqualTo(updatedVoucher.getVoucherType());
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.vouchermanagement"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/test")
                    .username("root")
                    .password("980726")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

}