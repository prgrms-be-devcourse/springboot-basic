package com.prgrms.repository.voucher;


import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.dto.discount.FixedDiscount;
import com.prgrms.util.KeyGenerator;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.prgrms.repository.voucher"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_byeol")
                    .username("root")
                    .password("7351")
                    .type(HikariDataSource.class)
                    .build();
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

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    private Voucher newFixVoucher;

    private int id = KeyGenerator.make();

    @BeforeAll
    void clean() {
        newFixVoucher = new FixedAmountVoucher(id, new FixedDiscount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 추가할 수 있다.")
    public void testInsert() {

        jdbcVoucherRepository.insert(newFixVoucher);

        var retrievedVoucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getVoucherId(), samePropertyValuesAs(newFixVoucher.getVoucherId()));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        var vouchers = jdbcVoucherRepository.getAllVoucher();
        assertThat(vouchers.getVoucherRegistry().isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    public void testFindById() {
        var voucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        assertThat(voucher.get().getVoucherId()).isEqualTo(newFixVoucher.getVoucherId());
    }

}
