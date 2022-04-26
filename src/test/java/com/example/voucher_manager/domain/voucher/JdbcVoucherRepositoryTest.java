package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.domain.customer.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig
@ActiveProfiles("deploy")
class JdbcVoucherRepositoryTest {

    @Container
    protected static final MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.0.19");

    @Configuration
    @ComponentScan(basePackages = "com.example.voucher_manager.domain.voucher")
    static class Config {

        @Bean
        public DataSource dataSource() {
            mysqlContainer.withInitScript("init.sql").start();

            return DataSourceBuilder.create()
                    .driverClassName(mysqlContainer.getDriverClassName())
                    .url(mysqlContainer.getJdbcUrl())
                    .username(mysqlContainer.getUsername())
                    .password(mysqlContainer.getPassword())
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }

    }

    @Autowired
    VoucherRepository voucherRepository;

    @AfterEach
    void clear() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("voucher가 database에 저장된다.")
    void insertTest() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        assertThat(voucherRepository.insert(voucher), not(Optional.empty()));
    }

    @Test
    @DisplayName("동일한 ID를 가지는 voucher는 저장될 수 없다.")
    void duplicatedInsertTest() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        Voucher voucher2 = new FixedAmountVoucher(voucher.getVoucherId(), 200L, VoucherType.FIXED);
        assertThrows(DuplicateKeyException.class, () -> voucherRepository.insert(voucher2));
    }

    @Test
    @DisplayName("Voucher Id를 통해 Voucher를 찾는다.")
    void findById() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        assertThat(voucherRepository.findById(voucher.getVoucherId()).get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("Database에 저장된 모든 Voucher를 반환한다.")
    void findAll() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 30L, VoucherType.PERCENT);
        voucherRepository.insert(voucher2);

        assertThat(voucherRepository.findAll(), containsInAnyOrder(
                samePropertyValuesAs(voucher),
                samePropertyValuesAs(voucher2)
        ));
    }

    @Test
    @DisplayName("특정 고객에게 바우처를 할당할 수 있다.")
    void update() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        voucher.provideToCustomer(UUID.randomUUID()); // 임의의 주인 할당
        voucherRepository.update(voucher);

        var find = voucherRepository.findById(voucher.getVoucherId()).get();

        assertThat(find.getOwnerId(), not(voucher.getOwnerId())); // 주인이 변경되었어야함
    }

    @Test
    @DisplayName("고객이 보유한 바우처 리스트를 조회할 수 있다.")
    void findVoucherListByCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 200L, VoucherType.FIXED);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 300L, VoucherType.FIXED);

        // 2개만 유저에게 할당
        voucher.provideToCustomer(customer.getCustomerId());
        voucher2.provideToCustomer(customer.getCustomerId());

        voucherRepository.insert(voucher);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        var voucherList = voucherRepository.findVoucherListByCustomer(customer);

        assertThat(voucherList, containsInAnyOrder(
                samePropertyValuesAs(voucher),
                samePropertyValuesAs(voucher2)
        ));
    }

    @Test
    @DisplayName("고객이 보유한 특정 바우처를 제거할 수 있다.")
    void deleteVoucherCustomerHas() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);

        voucher.provideToCustomer(customer.getCustomerId());
        voucher2.provideToCustomer(customer.getCustomerId());

        voucherRepository.insert(voucher);
        voucherRepository.insert(voucher2);

        voucherRepository.deleteVoucherByCustomer(voucher2, customer);

        var voucherList = voucherRepository.findVoucherListByCustomer(customer);
        assertThat(voucherList, hasSize(1)); // 1개만 남고
        assertThat(voucherList.get(0), samePropertyValuesAs(voucher)); // 2번이 제거 되었을 것
    }
}