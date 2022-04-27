package org.prgrms.voucherprgrms.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherprgrms.voucher.model.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedVoucherNamedJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherprgrms"}
    )
    static class AppConfig {
        @Bean
        public DataSource dataSource() {

            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2210/test-voucher_prgrms")
                    .username("test")
                    .password("test123")
                    .type(HikariDataSource.class)
                    .build();

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    VoucherNamedJdbcRepository voucherNamedJdbcRepository;

    @Autowired
    DataSource dataSource;

    Voucher newVoucher;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        var mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test123")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher_prgrms", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @Disabled
    @DisplayName("Connection check")
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("INSERT 쿼리 테스트")
    void voucherInsertTest() {
        voucherNamedJdbcRepository.insert(newVoucher);

        var findVoucher = voucherNamedJdbcRepository.findById(newVoucher.getVoucherId());

        assertThat(findVoucher.isEmpty(), is(false));
        assertThat(findVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("DELETE ALL 테스트")
    void deleteAllTest() {
        voucherNamedJdbcRepository.deleteAll();
        var voucherList = voucherNamedJdbcRepository.findAll();
        assertThat(voucherList.isEmpty(), is(true));
    }

}
