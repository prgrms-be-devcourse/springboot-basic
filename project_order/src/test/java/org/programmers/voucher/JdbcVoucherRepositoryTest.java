package org.programmers.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.programmers.customer", "org.programmers.voucher"}
    )
    static class Config {

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
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    List<Voucher> voucherList;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();

        voucherList = new ArrayList<>() {{
            add(new PercentDiscountVoucher(UUID.randomUUID(), 25));
            add(new FixedAmountVoucher(UUID.randomUUID(), 10));
            add(new FixedAmountVoucher(UUID.randomUUID(), 35));
        }};
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("바우처를 추가할 수 있다")
    @Order(1)
    public void insert() {
        // given
        Voucher voucher = voucherList.get(0);

        // when
        voucherRepository.insert(voucher);

        // then
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("총 바우처 개수를 셀 수 있다")
    @Order(2)
    void countAll() {
        // given
        voucherRepository.insert(voucherList.get(1));
        voucherRepository.insert(voucherList.get(2));

        // when
        int count = voucherRepository.countAll();

        // then
        assertThat(count, is(3));
    }

    @Test
    @DisplayName("fixed amount voucher의 총 개수를 셀 수 있다")
    @Order(3)
    void countFixed() {
        // given

        // when
        int count = voucherRepository.countFixed();

        // then
        assertThat(count, is(2));
    }

    @Test
    @DisplayName("percent discount voucher의 총 개수를 셀 수 있다")
    @Order(4)
    void countPercent() {
        // given

        // when
        int count = voucherRepository.countPercent();

        // then
        assertThat(count, is(1));
    }

    @Test
    @DisplayName("모든 바우처를 불러올 수 있다")
    @Order(5)
    void findAll() {
        // given

        // when
        List<Voucher> vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.size(), is(3));
    }

    @Test
    @DisplayName("id로 바우처를 불러올 수 있다")
    @Order(6)
    void findById() {
        // given
        Voucher voucher = voucherList.get(1);

        // when
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucher.getVoucherId());

        // then
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("모든 바우처를 삭제할 수 있다")
    @Order(7)
    void deleteAll() {
        // given

        // when
        voucherRepository.deleteAll();

        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(true));
    }

}