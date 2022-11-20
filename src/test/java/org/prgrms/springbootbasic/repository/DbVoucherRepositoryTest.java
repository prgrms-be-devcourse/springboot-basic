package org.prgrms.springbootbasic.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.*;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbVoucherRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = "org.prgrms.springbootbasic.repository")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
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
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DbVoucherRepository dbVoucherRepository;

    EmbeddedMysql embeddedMysql;

    UUID fixedId1 = UUID.randomUUID();
    long amount1;
    FixedAmountVoucher fixedAmountVoucher1;

    UUID fixedId2 = UUID.randomUUID();
    long amount2;
    FixedAmountVoucher fixedAmountVoucher2;

    UUID percentId1;
    long percent1;
    PercentAmountVoucher percentAmountVoucher1;

    UUID percentId2 = UUID.randomUUID();
    long percent2;
    PercentAmountVoucher percentAmountVoucher2;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_mgmt", classPathScripts("schema.sql"))
                .start();

        UUID fixedId1 = UUID.randomUUID();
        amount1 = 100L;
        fixedAmountVoucher1 = new FixedAmountVoucher(fixedId1, amount1);

        UUID fixedId2 = UUID.randomUUID();
        amount2 = 200L;
        fixedAmountVoucher2 = new FixedAmountVoucher(fixedId2, amount2);
        dbVoucherRepository.insert(fixedAmountVoucher1);
        dbVoucherRepository.insert(fixedAmountVoucher2);

        UUID percentId1 = UUID.randomUUID();
        percent1 = 10L;
        percentAmountVoucher1 = new PercentAmountVoucher(percentId1, percent1);

        UUID percentId2 = UUID.randomUUID();
        percent2 = 20L;
        percentAmountVoucher2 = new PercentAmountVoucher(percentId2, percent2);

        dbVoucherRepository.insert(percentAmountVoucher1);
        dbVoucherRepository.insert(percentAmountVoucher2);
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    void testInsert() {

        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher insertedFixedAmountVoucher = new FixedAmountVoucher(uuid, 100L);

        dbVoucherRepository.insert(insertedFixedAmountVoucher);
        assertThat(insertedFixedAmountVoucher.getVoucherId(), is(uuid));
    }

    @Test
    void testFindAll() {
        List<Voucher> allVouchers = dbVoucherRepository.findAll();

        Assertions.assertThat(allVouchers).extracting("voucherId", "quantity")
                .contains(Tuple.tuple(fixedAmountVoucher1.getVoucherId(), fixedAmountVoucher1.getQuantity()))
                .contains(Tuple.tuple(fixedAmountVoucher2.getVoucherId(), fixedAmountVoucher2.getQuantity()))
                .contains(Tuple.tuple(percentAmountVoucher1.getVoucherId(), percentAmountVoucher1.getQuantity()))
                .contains(Tuple.tuple(percentAmountVoucher2.getVoucherId(), percentAmountVoucher2.getQuantity()));
    }
}