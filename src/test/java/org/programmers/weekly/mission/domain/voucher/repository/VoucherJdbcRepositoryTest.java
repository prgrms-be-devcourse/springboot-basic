package org.programmers.weekly.mission.domain.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.weekly.mission.domain.voucher.model.FixedAmountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.model.VoucherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.weekly.mission.domain.voucher"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher_mgmt")
                    .username("test")
                    .password("test1234!")
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

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    Voucher newVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_voucher_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 추가할 수 있다")
    public void testInsert() {
        Voucher insert = voucherRepository.insert(newVoucher);

        Optional<Voucher> receivedVoucher = voucherRepository.findById(newVoucher.getVoucherId());

        assertThat(receivedVoucher.isEmpty(), is(false));
        assertThat(receivedVoucher.get(), samePropertyValuesAs(insert));
    }

    @Test
    @Order(3)
    @DisplayName("바우처를 전체 조회할 수 있다")
    public void testFindAll() {
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();
        assertThat(allVouchers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("아이디로 바우처를 조회할 수 있다")
    public void testFindById() {
        Optional<Voucher> voucher = voucherRepository.findById(newVoucher.getVoucherId());
        VoucherDto voucherDto = new VoucherDto(newVoucher.getVoucherId(), newVoucher.getType(), newVoucher.getDiscount());

        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get(), samePropertyValuesAs(voucherDto));
    }

    @Test
    @Order(5)
    @DisplayName("아이디로 바우처를 삭제할 수 있다")
    public void testDeleteById() {
        voucherRepository.deleteById(newVoucher.getVoucherId());
        Optional<Voucher> maybeVoucher = voucherRepository.findById(newVoucher.getVoucherId());

        assertThat(maybeVoucher.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("모든 바우처를 삭제할 수 있다")
    public void testDeleteAll() {
        voucherRepository.deleteAll();
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        assertThat(allVouchers, hasSize(0));
    }

}