package com.pppp0722.vouchermanagement.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.pppp0722.vouchermanagement.entity.voucher.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.entity.voucher.Voucher;
import com.pppp0722.vouchermanagement.repository.voucher.JdbcVoucherRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("JdbcVoucherRepository 단위 테스트")
class JdbcVoucherRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepositoryTest.class);

    @Configuration
    @ComponentScan
    static class config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2216/test-voucher_mgmt")
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

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    EmbeddedMysql embeddedMysql;

    Voucher newVoucher;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2216)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
            .start();

        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Voucher 추가")
    public void testInsert() {
        try {
            voucherRepository.createVoucher(newVoucher);
        } catch (BadSqlGrammarException e) {
            logger.error("Got BadSqlGrammarException error code -> {}",
                e.getSQLException().getErrorCode(), e);
        }

        Optional<Voucher> retrievedVoucher = voucherRepository.readVoucher(
            newVoucher.getVoucherId());
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("Voucher 조회")
    public void testFindAll() {
        List<Voucher> vouchers = voucherRepository.readVouchers();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("Voucher 삭제")
    public void testDelete() {
        Voucher deletedVoucher = voucherRepository.deleteVoucher(newVoucher);
        Optional<Voucher> retrievedVoucher = voucherRepository.readVoucher(
            deletedVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(true));
    }
}