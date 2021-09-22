package org.prgrms.kdtspringhw.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringhw.voucher.voucherObj.FixedAmountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.PercentDiscountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataBaseVoucherRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdtspringhw.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
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

        @Bean
        public DataBaseVoucherRepository dataBaseVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new DataBaseVoucherRepository(jdbcTemplate);
        }
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataBaseVoucherRepository dataBaseVoucherRepository;

    Voucher newFixedAmountVoucher;
    Voucher newPercentDiscountVoucher;

    @BeforeAll
    void setUp() {
        newFixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        newPercentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 25L);
    }

    @Test
    @Order(1)
    @DisplayName("모든 Voucher를 삭제할 수 있다.")
    void testDeleteAll(){
        dataBaseVoucherRepository.deleteAll();
        assertThat(mapToList(dataBaseVoucherRepository.returnAll()), hasSize(0));
    }

    @Test
    @Order(2)
    @DisplayName("Insert할 수 있다")
    void testInsert(){
        dataBaseVoucherRepository.insert(newFixedAmountVoucher);
        dataBaseVoucherRepository.insert(newPercentDiscountVoucher);
        assertThat(mapToList(dataBaseVoucherRepository.returnAll()), hasSize(2));

    }

    private List<Voucher> mapToList(Map<UUID, Voucher> returnAll) {
        List<Voucher> list = new ArrayList<>(returnAll.values());
        return list;
    }

    @Test
    @Order(3)
    @DisplayName("Id로 조회할 수 있다")
    void testFindByid(){
        var testVoucher1 = dataBaseVoucherRepository.findById(newFixedAmountVoucher.getVoucherId());
        var testVoucher2 = dataBaseVoucherRepository.findById(newPercentDiscountVoucher.getVoucherId());
        assertThat(testVoucher1.isEmpty(), is(false));
        assertThat(testVoucher2.isEmpty(), is(false));
        assertThat(testVoucher1.get(), samePropertyValuesAs(newFixedAmountVoucher));
        assertThat(testVoucher2.get(), samePropertyValuesAs(newPercentDiscountVoucher));
    }

    @Test
    @Order(4)
    @DisplayName("모든 voucher를 조회할 수 있다")
    void testReturnAll(){
        var testVoucherList = mapToList(dataBaseVoucherRepository.returnAll());
        assertThat(testVoucherList, containsInAnyOrder(samePropertyValuesAs(newFixedAmountVoucher), samePropertyValuesAs(newPercentDiscountVoucher)));
    }
}