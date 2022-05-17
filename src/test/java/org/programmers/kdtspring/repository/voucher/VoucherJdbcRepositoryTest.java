package org.programmers.kdtspring.repository.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;
    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void setUp() {
        customerRepository.deleteAll();
        voucherJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("FixedVoucher 생성")
    void testCreateFixedVoucher() {
        voucherJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), null, 1000, VoucherType.FixedAmountVoucher.name()));
        var vouchers = voucherJdbcRepository.findAll();

        Assertions.assertThat(vouchers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("PercentVoucher 생성")
    void testCreatePercentVoucher() {
        voucherJdbcRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), null, 1000, VoucherType.FixedAmountVoucher.name()));
        var vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("고객으로 바우처 찾기")
    void testFindByCustomerId() {
        var customerId = UUID.randomUUID();
        Customer customer = customerRepository.insert(new Customer(customerId, "kim", "iop1996@naver.com", LocalDateTime.now()));
        voucherJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), customerId, 1000, VoucherType.FixedAmountVoucher.name()));
        voucherJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), customerId, 1000, VoucherType.FixedAmountVoucher.name()));
        voucherJdbcRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), customerId, 1000, VoucherType.FixedAmountVoucher.name()));

        List<Voucher> vouchersForCustomer = voucherJdbcRepository.findByCustomer(customer);

        assertThat(vouchersForCustomer.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("하나 삭제")
    void TestDeleteOne() {
        var voucherId = UUID.randomUUID();
        voucherJdbcRepository.insert(new FixedAmountVoucher(voucherId, null, 1000, VoucherType.FixedAmountVoucher.name()));
        Optional<Voucher> voucher = voucherJdbcRepository.findById(voucherId);

        voucherJdbcRepository.deleteOne(voucher.get());

        List<Voucher> all = voucherJdbcRepository.findAll();
        assertThat(all.isEmpty()).isTrue();
    }

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.kdtspring"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_mgmt")
                    .username("root")
                    .password("xngosem258!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
}