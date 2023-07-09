package com.prgrms.repository.voucher;


import com.prgrms.model.voucher.FixedAmountVoucher;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.dto.discount.FixedDiscount;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.prgrms.repository.voucher"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create().url("jdbc:mysql://localhost/order_byeol").username("root").password("7351").type(HikariDataSource.class).build();
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

    private int id = 1;

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
    @DisplayName("바우처를 추가한 결과 반환하는 바우처의 아이디와 추가한 바우처의 아이디는 같다.")
    public void insert_VoucherId_EqualsNewVoucherId() {

        jdbcVoucherRepository.insert(newFixVoucher);

        var retrievedVoucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get().getVoucherId(), samePropertyValuesAs(newFixVoucher.getVoucherId()));
    }

    @Test
    @Order(3)
    @DisplayName("데이터베이스에 몇 개의 데이터를 저장한 후 전체 고객을 조회한 결과는 빈 값을 반환하지 않는다.")
    public void findAll_Vouchers_NotEmpty() {
        var vouchers = jdbcVoucherRepository.getAllVoucher();
        assertThat(vouchers.getVouchers().isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("데이터베이스에 존재하는 회원의 아이디로 검색했을 때 반환하는 바우처의 아이디와 검색할 때 사용한 바우처의 아이디는 같다.")
    public void findById_ExistingVoucherId_EqualsSearchId() {
        var voucher = jdbcVoucherRepository.findById(newFixVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        assertThat(voucher.get().getVoucherId()).isEqualTo(newFixVoucher.getVoucherId());
    }
}
