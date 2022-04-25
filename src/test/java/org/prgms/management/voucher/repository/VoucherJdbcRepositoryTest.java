package org.prgms.management.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.entity.VoucherCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local-db")
class VoucherJdbcRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgms.management.voucher"})
    @EnableTransactionManagement
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    DataSource dataSource;

    List<Voucher> newVouchers = new ArrayList<>();

    @BeforeAll
    void setup() {
        voucherJdbcRepository.deleteAll();
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 1000, "voucher-test1", "fixed", LocalDateTime.now()).orElse(null));
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 500, "voucher-test2", "fixed", LocalDateTime.now()).orElse(null));
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 99, "voucher-test3", "percent", LocalDateTime.now()).orElse(null));
        newVouchers.add(VoucherCreator.createVoucher(UUID.randomUUID(), 50, "voucher-test4", "percent", LocalDateTime.now()).orElse(null));
    }

    @Test
    @Order(1)
    void testConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 추가 할 수 있다.")
    void insert() {
        newVouchers.forEach(v -> {
            var voucher = voucherJdbcRepository.insert(v);
            assertThat(voucher.isEmpty(), is(false));
            assertThat(voucher.get().getVoucherId(), is(v.getVoucherId()));
        });
    }

    @Test
    @Order(3)
    @DisplayName("중복된 바우처를 추가 할 수 없다.")
    void insertDuplicateVoucher() {
        newVouchers.forEach(v -> {
            var voucher = voucherJdbcRepository.insert(v);
            assertThat(voucher.isEmpty(), is(true));
        });
    }

    @Test
    @Order(4)
    @DisplayName("전체 바우처를 조회 할 수 있다.")
    void findAll() {
        var customers = voucherJdbcRepository.findAll();
        assertThat(customers.size(), is(newVouchers.size()));
    }

    @Test
    @Order(5)
    @DisplayName("아이디로 바우처를 조회 할 수 있다.")
    void findById() {
        newVouchers.forEach(v -> {
            System.out.println("voucher id -> " + v.getVoucherId());
            var voucher = voucherJdbcRepository.findById(
                    v.getVoucherId());
            assertThat(voucher.isEmpty(), is(false));
        });
    }

    @Test
    @Order(6)
    @DisplayName("시간으로 바우처를 조회 할 수 있다.")
    void findByCreatedAt() {
        newVouchers.forEach(v -> {
            var customer = voucherJdbcRepository.findByCreatedAt(
                    v.getCreatedAt());
            assertThat(customer.isEmpty(), is(false));
        });
    }

    @Test
    @Order(7)
    @DisplayName("타입으로 바우처를 조회 할 수 있다.")
    void findByType() {
        newVouchers.forEach(v -> {
            var customer = voucherJdbcRepository.findByType(
                    v.getType());
            assertThat(customer.isEmpty(), is(false));
        });
    }

    @Test
    @Order(8)
    @DisplayName("바우처를 수정 할 수 있다.")
    void update() {
        var name = "updated-voucher";
        newVouchers.get(0).changeName(name);
        var voucher = voucherJdbcRepository.update(
                newVouchers.get(0));

        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getName(), is(name));
    }

    @Test
    @Order(9)
    @DisplayName("바우처를 삭제 할 수 있다.")
    void delete() {
        var voucher = voucherJdbcRepository.delete(
                newVouchers.get(0));
        assertThat(voucher.isEmpty(), is(false));
    }

    @Test
    @Order(10)
    @DisplayName("모든 바우처를 삭제 할 수 있다.")
    void deleteAll() {
        voucherJdbcRepository.deleteAll();
        var vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @Order(11)
    @DisplayName("삭제한 바우처는 검색 할 수 없다.")
    void findDeleted() {
        newVouchers.forEach(v -> {
            var voucher = voucherJdbcRepository.findById(v.getVoucherId());
            assertThat(voucher.isEmpty(), is(true));
            voucher = voucherJdbcRepository.findByName(v.getName());
            assertThat(voucher.isEmpty(), is(true));
        });
    }
}