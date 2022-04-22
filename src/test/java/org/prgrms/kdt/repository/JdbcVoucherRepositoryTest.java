package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.repository"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    FixedAmountVoucher fixedAmountVoucher;

    PercentDiscountVoucher percentDiscountVoucher;

    @BeforeAll
    void clean() {
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
        percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(Version.v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 넣을 수 있다.")
    void insert() {
        var insertVoucher = jdbcVoucherRepository.insert(fixedAmountVoucher);
        var receiveVoucher = jdbcVoucherRepository.getByVoucherId(insertVoucher.getVoucherId());
        assertThat(insertVoucher.getVoucherId(), equalTo(receiveVoucher.get().getVoucherId()));
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 바우처Id로 가져 올 수 있다.")
    void getVoucher() {
       Voucher voucher = jdbcVoucherRepository.getByVoucherId(fixedAmountVoucher.getVoucherId()).get();
       assertThat(voucher.getVoucherId(), equalTo(fixedAmountVoucher.getVoucherId()));
    }

    @Test
    @Order(2)
    @DisplayName("전체 바우처 리스트를 가져올 수 있다.")
    void getVoucherList() {
        var voucherList = jdbcVoucherRepository.getVoucherList();
        assertThat(voucherList.isEmpty(), is(false));
        assertThat(voucherList.size(), equalTo(1));
    }

    @Test
    @Order(3)
    @DisplayName("바우처를 삭제 할 수 있다.")
    void delete() {
        jdbcVoucherRepository.delete(fixedAmountVoucher);
        var voucherList = jdbcVoucherRepository.getVoucherList();
        assertThat(voucherList.isEmpty(), is(true));
    }
}