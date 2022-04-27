package org.programmer.kdtspringboot.voucher;

import com.wix.mysql.EmbeddedMysql;
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
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcTemplateRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.programmer.kdtspringboot.voucher"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
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
    VoucherJdbcTemplateRepository voucherJdbcTemplateRepository;

    @Autowired
    DataSource dataSource;

    Voucher voucher;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        //임베디드 실습
        var mysqlConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void tearDown() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("HiKARI 확인")
    void hikariConnectionPoolTest() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("FixedAmountVoucher 추가할 수 있음")
    void insertFixedAmountVoucherTest() {
        voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        voucherJdbcTemplateRepository.insert(voucher);

        var retrievedCustomer = voucherJdbcTemplateRepository.findById(voucher.getVoucherId());
        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get()).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @Order(3)
    @DisplayName("PercentDiscountVoucher 추가할 수 있음")
    void insertPercentDiscountVoucherTest() {
        voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        voucherJdbcTemplateRepository.insert(voucher);

        var retrievedCustomer = voucherJdbcTemplateRepository.findById(voucher.getVoucherId());
        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get()).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @Order(4)
    @DisplayName("모든 Voucher 조회할 수 있음")
    void findAllVoucherTest() /*throws InterruptedException*/ {
        var vouchers = voucherJdbcTemplateRepository.findAll();
        assertThat(vouchers).isNotEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("VoucherId 로 Voucher 조회할 수 있음")
    void findIdTest() {
        voucher = new FixedAmountVoucher(UUID.randomUUID(), 30L);
        voucherJdbcTemplateRepository.insert(voucher);

        var getVoucher1 = voucherJdbcTemplateRepository.findById(voucher.getVoucherId());
        assertThat(getVoucher1).isNotEmpty();

        var getVoucher2 = voucherJdbcTemplateRepository.findById(UUID.randomUUID());
        assertThat(getVoucher2).isEmpty();
    }
}
