package org.programmers.kdtspring.repository.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class voucherJdbcRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"org.programmers.kdtspring"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/voucher_mgmt")
                    .username("root")
                    .password("xngosem258!")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void setUp() {
        voucherJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("FixedVoucher 생성")
    void TestCreateFixedVoucher() {
        voucherJdbcRepository.save(new FixedAmountVoucher(1L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));
        var vouchers = voucherJdbcRepository.findAll();

        Assertions.assertThat(vouchers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("PercentVoucher 생성")
    void TestPercentVoucher() {
        voucherJdbcRepository.save(new FixedAmountVoucher(2L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));
        var vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("고객으로 바우처 찾기")
    void TestFindByCustomerId() {
        voucherJdbcRepository.save(new FixedAmountVoucher(1L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));
        voucherJdbcRepository.save(new FixedAmountVoucher(2L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));
        voucherJdbcRepository.save(new FixedAmountVoucher(3L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));

        Optional<Customer> customer = customerRepository.findById(1L);
        List<Voucher> vouchersForCustomer = voucherJdbcRepository.findByCustomer(customer.get());

        assertThat(vouchersForCustomer.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("하나 삭제")
    void TestDeleteOne() {
        voucherJdbcRepository.save(new FixedAmountVoucher(1L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));
        voucherJdbcRepository.save(new FixedAmountVoucher(2L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));
        voucherJdbcRepository.save(new FixedAmountVoucher(3L, 1L, 1000, VoucherType.FixedAmountVoucher.name()));

        Optional<Voucher> voucher = voucherJdbcRepository.findById(1L);

        voucherJdbcRepository.deleteOne(voucher.get());


        List<Voucher> all = voucherJdbcRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

}







