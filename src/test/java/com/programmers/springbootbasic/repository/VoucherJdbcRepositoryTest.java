package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.configuration.DataSourceProperties;
import com.programmers.springbootbasic.configuration.YamlPropertiesFactory;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.Voucher;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.springbootbasic"})
    static class Config {

        @Autowired
        private DataSourceProperties dataSourceProperties;

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(dataSourceProperties.getUrl())
                    .username(dataSourceProperties.getUsername())
                    .password(dataSourceProperties.getPassword())
                    .type(HikariDataSource.class)
                    .build();
        }

    }

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    void initBeforeTest() {
        voucherJdbcRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("HikariDataSource 빈을 정상적으로 주입할 수 있다.")
    void testDataSourceConnection() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");

        fixedAmountVoucher = new Voucher(UUID.randomUUID(), 1000L, null, 1);
        percentDiscountVoucher = new Voucher(UUID.randomUUID(), null, 15, 2);
    }

    @Test
    @Order(2)
    @DisplayName("서로 다른 종류의 두 할인권을 할인권 테이블에 저장할 수 있다.")
    void testInsert() {
        voucherJdbcRepository.insert(fixedAmountVoucher);
        voucherJdbcRepository.insert(percentDiscountVoucher);
    }

    @Test
    @Order(3)
    @DisplayName("존재하는 할인권 아이디에 대해 일치하는 할인권 객체를 반환하고 존재하지 않는 할인권은 빈 객체를 반환한다.")
    void testFindById() {
        Optional<Voucher> foundId = voucherJdbcRepository.findById(fixedAmountVoucher.getVoucherId());
        Optional<Voucher> notFoundId = voucherJdbcRepository.findById(UUID.randomUUID());

        assertThat(foundId.get().getVoucherId().toString()).isEqualTo(fixedAmountVoucher.getVoucherId().toString());
        assertThat(notFoundId.isEmpty()).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("저장된 할인권 객체의 아이디로 해당 객체를 할인권 테이블에서 삭제할 수 있다.")
    void testDeleteById() {
        voucherJdbcRepository.deleteById(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @Order(5)
    @DisplayName("현재 할인권 테이블에 저장된 모든 객체를 정상적으로 불러올 수 있다.")
    void testFindAll() {
        List<Voucher> allCustomers = voucherJdbcRepository.findAll();

        assertThat(allCustomers.size()).isEqualTo(1);
        assertThat(allCustomers.get(0).getVoucherId().equals(percentDiscountVoucher.getVoucherId())).isTrue();
    }

}