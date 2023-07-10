package org.prgms.vouchermanagement.voucher.domain.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
class VoucherNamedJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(VoucherNamedJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.vouchermanagement.voucher", "org.prgms.vouchermanagement.global"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3308/test_voucher")
                    .username("test")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public VoucherNamedJdbcRepository voucherNamedJdbcRepository(DataSource dataSource) {
            return new VoucherNamedJdbcRepository(dataSource);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    VoucherNamedJdbcRepository voucherNamedJdbcRepository;

    EmbeddedMysql embeddedMysql;
    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(3308)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_voucher", classPathScript("VoucherSchema.sql"))
                .start();

        percentDiscountVoucher = new Voucher(UUID.randomUUID(), 60, VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE);
        fixedAmountVoucher = new Voucher(UUID.randomUUID(), 1000, VoucherType.FIXED_AMOUNT_VOUCHER_TYPE);
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Voucher ConnectionPool 확인")
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("Voucher save 기능 test")
    void testSave() {
        voucherNamedJdbcRepository.save(fixedAmountVoucher);

        Optional<Voucher> savedVoucher = voucherNamedJdbcRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(savedVoucher).isNotEmpty();
        assertThat(savedVoucher.get()).usingRecursiveComparison().isEqualTo(fixedAmountVoucher);
    }

    @Test
    @Order(3)
    @DisplayName("Voucher get voucherList 기능 test")
    void testGetVoucherList() {
        voucherNamedJdbcRepository.save(percentDiscountVoucher);

        List<Voucher> voucherList = voucherNamedJdbcRepository.findAll();
        assertThat(voucherList).isNotEmpty().hasSize(2);
    }
}
