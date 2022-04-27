package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.configuration.DataSourceProperties;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerVoucherLogJdbcRepositoryTest {

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
    CustomerVoucherLogJdbcRepository customerVoucherLogJdbcRepository;
    @Autowired
    CustomerJdbcRepository customerJdbcRepository;
    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    Customer customerA, customerB;
    Voucher firstVoucherForCustomerA, secondVoucherForCustomerA;

    @BeforeAll
    void initBeforeTest() {
        customerJdbcRepository.deleteAll();
        voucherJdbcRepository.deleteAll();

        customerA = new Customer("chlwlgns524","jihun");
        customerB = new Customer("chlwlgns514","jihoon");
        firstVoucherForCustomerA = new Voucher(UUID.randomUUID(), 1500L, null, 1);
        secondVoucherForCustomerA = new Voucher(UUID.randomUUID(), null, 15, 2);

        customerJdbcRepository.insert(customerA);
        customerJdbcRepository.insert(customerB);
        voucherJdbcRepository.insert(firstVoucherForCustomerA);
        voucherJdbcRepository.insert(secondVoucherForCustomerA);
    }

    @Test
    @Order(1)
    @DisplayName("HikariDataSource 빈을 정상적으로 주입할 수 있다.")
    void testDataSourceConnection() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("할인권을 고객에게 할당한 로그를 customer_voucher_log 테이블에 저장할 수 있다.")
    void testInsert() {
        customerVoucherLogJdbcRepository.insert(customerA.getCustomerId(), firstVoucherForCustomerA.getVoucherId());
        customerVoucherLogJdbcRepository.insert(customerA.getCustomerId(), secondVoucherForCustomerA.getVoucherId());
    }

    @Test
    @Order(3)
    @DisplayName("현재까지 저장된 로그를 정상적으로 불러올 수 있다.")
    void testFindByCustomerId() {
        List<CustomerVoucherLog> foundLog = customerVoucherLogJdbcRepository.findByCustomerId(customerA.getCustomerId());
        List<CustomerVoucherLog> notFoundLog = customerVoucherLogJdbcRepository.findByCustomerId(customerB.getCustomerId());

        assertThat(foundLog.size()).isEqualTo(2);
        assertThat(foundLog).extracting("customerId").containsOnly("chlwlgns524");
        assertThat(foundLog).extracting("voucherId")
                .containsOnly(firstVoucherForCustomerA.getVoucherId(),
                            secondVoucherForCustomerA.getVoucherId());

        assertThat(notFoundLog.size()).isEqualTo(0);
    }

    @Test
    @Order(4)
    @DisplayName("할인권 아이디로 로그를 조회할 수 있다.")
    void testFindByVoucherId() {
        Optional<CustomerVoucherLog> foundLog = customerVoucherLogJdbcRepository.findByVoucherId(firstVoucherForCustomerA.getVoucherId());
        Optional<CustomerVoucherLog> notFoundLog = customerVoucherLogJdbcRepository.findByVoucherId(UUID.randomUUID());

        assertThat(foundLog.isPresent()).isTrue();
        assertThat(notFoundLog.isPresent()).isFalse();
    }

    @Test
    @Order(5)
    @DisplayName("현재까지 저장된 모든 로그를 조회할 수 있다.")
    void testFindAll() {
        List<CustomerVoucherLog> allLogs = customerVoucherLogJdbcRepository.findAll();

        assertThat(allLogs).extracting("customerId").containsOnly("chlwlgns524");
        assertThat(allLogs).extracting("voucherId")
                .containsOnly(firstVoucherForCustomerA.getVoucherId(),
                        secondVoucherForCustomerA.getVoucherId());
    }

    @Test
    @Order(6)
    @DisplayName("특정 고객이 보유한 할인권을 모두 조회할 수 있다.")
    void testFindVouchersByCustomerId() {
        List<Voucher> forCustomerA = customerVoucherLogJdbcRepository.findVouchersByCustomerId(customerA.getCustomerId());
        List<Voucher> forCustomerB = customerVoucherLogJdbcRepository.findVouchersByCustomerId(customerB.getCustomerId());

        assertThat(forCustomerA.size()).isEqualTo(2);
        assertThat(forCustomerB.isEmpty()).isTrue();
    }

    @Test
    @Order(7)
    @DisplayName("특정 할인권을 갖고 있는 고객을 조회할 수 있다.")
    void testFindCustomerByVoucherId() {
        Optional<Customer> maybeCustomerA = customerVoucherLogJdbcRepository.findCustomerByVoucherId(firstVoucherForCustomerA.getVoucherId());
        Optional<Customer> maybeAlsoCustomerA = customerVoucherLogJdbcRepository.findCustomerByVoucherId(secondVoucherForCustomerA.getVoucherId());

        assertThat(maybeCustomerA.get().getCustomerId()).isEqualTo(maybeAlsoCustomerA.get().getCustomerId());
    }

}