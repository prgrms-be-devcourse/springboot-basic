package org.prgrms.kdt.repository.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;

import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.config.Charset.UTF8;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/week1")
                    .username("test")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

    }

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    VoucherEntity newVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newVoucher = VoucherEntity.toEntity(VoucherType.valueOf("FIXED_AMOUNT_VOUCHER").makeVoucher(1000));
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(3306)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 발급할 수 있다.")
    public void testInsert() {

        try {
            jdbcVoucherRepository.insert(newVoucher);
        } catch (BadSqlGrammarException e) {
            e.getSQLException().getErrorCode();
        }
        var retrievedVoucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    public void testFindAll() {
        var customers = jdbcVoucherRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 아이디로 바우처를 조회할 수 있다.")
    public void testFindByName() {
        var voucher = jdbcVoucherRepository.findById(newVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));

        var unknown = jdbcVoucherRepository.findById(voucher.get().getVoucherId());
        assertThat(unknown.isEmpty(), is(true));
    }

}