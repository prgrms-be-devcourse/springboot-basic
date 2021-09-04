package org.prgms.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_10;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.prgms.voucher.VoucherType.FIXED_AMOUNT;
import static org.prgms.voucher.VoucherType.PERCENT_DISCOUNT;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgms.voucher"}
    )
    static class config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
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
    FixedAmountVoucher newFixedAmount;
    PercentDiscountVoucher newPercentDiscount;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newFixedAmount = new FixedAmountVoucher(UUID.randomUUID(), 100, FIXED_AMOUNT);
        newPercentDiscount = new PercentDiscountVoucher(UUID.randomUUID(), 10, PERCENT_DISCOUNT);
        var mysqlConfig = aMysqldConfig(v5_7_10)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    @DisplayName("FixedAmountVoucher를 추가할 수 있다. ")
    public void testInsert() {
        voucherJdbcRepository.save(newFixedAmount);
        var retrievedVoucher = voucherJdbcRepository.findById(newFixedAmount.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));

    }

    @Test
    @Order(2)
    @DisplayName("PercentDiscountVoucher를 추가할 수 있다. ")
    public void testInsert2() {
        voucherJdbcRepository.save(newPercentDiscount);
        var retrievedVoucher = voucherJdbcRepository.findById(newPercentDiscount.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("전체 Voucher를 조회할 수 있다")
    public void testFindAll() {
        var vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
    }


}
