package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
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

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JDBCVoucherRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(JDBCVoucherRepositoryTest.class);
    @Autowired
    JDBCVoucherRepository jdbcVoucherRepository;
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
        jdbcVoucherRepository.save(newVoucher);
        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getVoucherId()).isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @Order(3)
    @DisplayName("퍼센트 할인 바우처를 추가할 수 있다.")
    void savePercentVoucherSucceed() {
        Voucher newVoucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(50), VoucherType.PERCENT);
        jdbcVoucherRepository.save(newVoucher);
        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getVoucherId()).isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @Order(4)
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void finsAllVoucherSucceed() {
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.isEmpty()).isFalse();
    }

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.vouchermanagement.voucher.repository"}
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