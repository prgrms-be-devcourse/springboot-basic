package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.VoucherDTO;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.springbootbasic.repository"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_mgmt?useUnicode=true&serverTimezone=UTC")
                    .username("test")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }
    /*
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
     */
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    VoucherDTO fixedAmountVoucher;
    VoucherDTO percentDiscountVoucher;

    @BeforeAll
    void initBeforeTest() {
        voucherJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testDataSourceConnection() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    void testInsert() {
        fixedAmountVoucher = new VoucherDTO(UUID.randomUUID(), 1000L, null, 1);
        percentDiscountVoucher = new VoucherDTO(UUID.randomUUID(), null, 15, 2);

        voucherJdbcRepository.insert(fixedAmountVoucher);
        voucherJdbcRepository.insert(percentDiscountVoucher);
    }

    @Test
    @Order(3)
    void testFindById() {
        Optional<VoucherDTO> foundId = voucherJdbcRepository.findById(fixedAmountVoucher.getVoucherId());
        Optional<VoucherDTO> notFoundId = voucherJdbcRepository.findById(UUID.randomUUID());

        assertThat(foundId.get().getVoucherId().toString()).isEqualTo(fixedAmountVoucher.getVoucherId().toString());
        assertThat(notFoundId.isEmpty()).isTrue();
    }

    @Test
    @Order(4)
    void testFindAll() {
        voucherJdbcRepository.findAll().forEach(System.out::println);
    }

}