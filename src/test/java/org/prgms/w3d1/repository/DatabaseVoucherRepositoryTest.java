package org.prgms.w3d1.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.w3d1.model.customer.CustomerJdbcRepository;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.PercentDiscountVoucher;
import org.prgms.w3d1.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.w3d1.model.voucher"})
    static class config {

        @Bean
        public DataSource dataSource(){
            return DataSourceBuilder.create().url("jdbc:mysql://localhost/my_order_mgmt")
                .username("root")
                .password("1111")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DatabaseVoucherRepository databaseVoucherRepository(JdbcTemplate jdbcTemplate){
            return new DatabaseVoucherRepository(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DatabaseVoucherRepository databaseVoucherRepository;

    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    void setUp(){
        fixedAmountVoucher = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        percentDiscountVoucher = PercentDiscountVoucher.of(UUID.randomUUID(), 25L);

    }

    @BeforeEach
    void methodSetup(){
        databaseVoucherRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Id를 통해서 바우처 조회")
    void findById() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucher = databaseVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(testVoucher.isEmpty(), is(false));
        assertThat(testVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 DBMS에 저장")
    void save() {
        databaseVoucherRepository.save(fixedAmountVoucher);

        var testVoucher = databaseVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(testVoucher.isEmpty(), is(false));
        assertThat(testVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("모든 바우처 꺼내기")
    void findAll() {
        databaseVoucherRepository.save(fixedAmountVoucher);
        databaseVoucherRepository.save(percentDiscountVoucher);

        var testVouchers = databaseVoucherRepository.findAll();

        assertThat(testVouchers, containsInAnyOrder(samePropertyValuesAs(fixedAmountVoucher), samePropertyValuesAs(percentDiscountVoucher)));
    }
}