package org.prgrms.kdt;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher"}
    )
    static class config{
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("kdt")
                    .password("")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    Voucher newFixedVoucher;
    Voucher newPercentVoucher;

    @BeforeAll
    void clean(){
        newFixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool(){
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 삽입할 수 있다")
    public void testInsert(){
        jdbcVoucherRepository.insert(newFixedVoucher);
        jdbcVoucherRepository.insert(newPercentVoucher);

        var retrievedFixedAmountVoucher = jdbcVoucherRepository.findById(newFixedVoucher.getId());
        var retrievedPercentAmountVoucher = jdbcVoucherRepository.findById(newPercentVoucher.getId());

        assertThat(retrievedFixedAmountVoucher.isEmpty(), is(false));
        assertThat(retrievedPercentAmountVoucher.isEmpty(), is(false));

        assertThat(retrievedFixedAmountVoucher.get(), samePropertyValuesAs(newFixedVoucher));
        assertThat(retrievedPercentAmountVoucher.get(), samePropertyValuesAs(newPercentVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("타입별 바우처 리스트 조회")
    public void testGetListByType(){
        var allVouchersList = jdbcVoucherRepository.getListByType(VoucherType.ALL);
        assertThat(allVouchersList.isEmpty(), is(false));

        var fixedVouchersList = jdbcVoucherRepository.getListByType(VoucherType.FIXED);
        assertThat(fixedVouchersList.isEmpty(), is(false));

        var percentVoucherList = jdbcVoucherRepository.getListByType(VoucherType.PERCENT);
        assertThat(percentVoucherList.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("id로 바우처 조회")
    public void testFindById() {
        var fixedVoucher = jdbcVoucherRepository.findById(newFixedVoucher.getId());
        assertThat(fixedVoucher.isEmpty(), is(false));
        assertThat(fixedVoucher.get(), samePropertyValuesAs(newFixedVoucher));

        var percentVoucher = jdbcVoucherRepository.findById(newPercentVoucher.getId());
        assertThat(percentVoucher.isEmpty(), is(false));
        assertThat(percentVoucher.get(), samePropertyValuesAs(newPercentVoucher));
    }

}
