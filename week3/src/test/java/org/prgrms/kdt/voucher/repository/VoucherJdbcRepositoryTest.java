package org.prgrms.kdt.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.blacklist", "org.prgrms.kdt.io"})
    static class Config {
        @Bean
        public DataSource datasource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    DataSource dataSource;

    FixedAmountVoucher fixedAmountVoucher;

    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeAll
    void setUp() {
        voucherJdbcRepository.deleteAll();
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
    }

    @Test
    @Order(1)
    @DisplayName("FixedAmountVoucher 바우처를 추가할 수 있다.")
    public void testFixedAmountVoucherInsert() throws IOException {
        var fixedVoucherId = fixedAmountVoucher.getVoucherId();

        voucherJdbcRepository.insert(fixedAmountVoucher);
        var retrievedFixedVoucher = voucherJdbcRepository.findById(fixedVoucherId);

        assertThat(retrievedFixedVoucher.isEmpty(), is(false));
        assertThat(retrievedFixedVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("PercentDiscountVoucher 바우처를 추가할 수 있다.")
    public void testPercentDiscountVoucherInsert() throws IOException {
        var percentDiscountVoucherId = percentDiscountVoucher.getVoucherId();

        voucherJdbcRepository.insert(percentDiscountVoucher);
        var retrievedPercentDiscountVoucher = voucherJdbcRepository.findById(percentDiscountVoucherId);

        assertThat(retrievedPercentDiscountVoucher.isEmpty(), is(false));
        assertThat(retrievedPercentDiscountVoucher.get(), samePropertyValuesAs(percentDiscountVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() 로 전체 바우처 정보 조회하기")
    public void testFindAll() throws IOException {
        var vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers.isEmpty(), is(false));
    }


    @Test
    @DisplayName("findById() 로 하나의 바우처 정보를 조회하기")
    public void testFindById() {
        var voucher = voucherJdbcRepository.findById(percentDiscountVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        var unknown = voucherJdbcRepository.findById(UUID.randomUUID());
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("전체 바우처 리스트 삭제 (확인 후 disabled )")
    @Disabled
    public void testDeleteAll() throws IOException {
        voucherJdbcRepository.deleteAll();
        var vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers.isEmpty(), is(true));
    }

}