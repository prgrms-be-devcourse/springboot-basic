package com.example.voucher_manager.domain.voucher;

import com.example.voucher_manager.MySqlContainerInitializer;
import com.example.voucher_manager.domain.customer.CustomerRepository;
import com.example.voucher_manager.domain.customer.JdbcCustomerRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig
//@ContextConfiguration(initializers = {MySqlContainerInitializer.class})
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
    JdbcVoucherRepository voucherRepository;

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
    @DisplayName("바우처의 정보를 갱신할 수 있다.")
    void update() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        voucher.provideToCustomer(UUID.randomUUID()); // 임의의 주인 할당
        voucherRepository.update(voucher);

        var find = voucherRepository.findById(voucher.getVoucherId()).get();

        assertThat(find.getOwnerId(), not(voucher.getOwnerId())); // 주인이 변경되었어야함
    }
}