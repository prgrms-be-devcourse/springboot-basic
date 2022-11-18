package org.prgrms.kdt.storage;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("prod")
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcVoucherStorageTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.storage"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_mgmt")
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
    }

    @Autowired
    VoucherStorage jdbcVoucherStorage;

    @Autowired
    DataSource dataSource;

    String customerId;
    Voucher voucher;
    String voucherId;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        voucherId = UUID.randomUUID().toString();
        customerId = UUID.randomUUID().toString();
        voucher = new FixedAmountVoucher(voucherId, 1000, customerId);
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("voucher_mgmt", classPathScript("voucher.sql"))
                .start();

        jdbcVoucherStorage.save(voucher);
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("소유하지 않은 바우처를 새로 추가할 수 있다.")
    void test1() {
        String newVoucherId = UUID.randomUUID().toString();
        Voucher newVoucher = new PercentDiscountVoucher(newVoucherId, 40);

        jdbcVoucherStorage.save(newVoucher);

        jdbcVoucherStorage.findById(newVoucherId)
                .ifPresent(findVoucher ->
                        assertThat(newVoucher).usingRecursiveComparison()
                                .isEqualTo(findVoucher));
    }

    @Test
    @DisplayName("소유한 고객이 있는 바우처를 새로 추가할 수 있다.")
    void test2() {
        String newVoucherId = UUID.randomUUID().toString();
        String newCustomerId = UUID.randomUUID().toString();
        Voucher newVoucher = new FixedAmountVoucher(newVoucherId, 3000, newCustomerId);

        jdbcVoucherStorage.save(newVoucher);

        jdbcVoucherStorage.findById(newVoucherId)
                .ifPresent(findVoucher ->
                        assertThat(newVoucher).usingRecursiveComparison()
                                .isEqualTo(findVoucher));

    }

    @Test
    @DisplayName("생성된 바우처를 모두 조회할 수 있다.")
    void test3() {
        List<Voucher> voucherList = jdbcVoucherStorage.findAll();

        assertFalse(voucherList.isEmpty());
    }

    @Test
    @DisplayName("바우처 ID를 사용하여 특정 바우처를 삭제할 수 있다.")
    void test4() {
        String deleteVoucherId = UUID.randomUUID().toString();
        Voucher deleteVoucher = new PercentDiscountVoucher(deleteVoucherId, 50, UUID.randomUUID().toString());
        jdbcVoucherStorage.save(deleteVoucher);

        jdbcVoucherStorage.deleteById(deleteVoucherId);

        assertEquals(Optional.empty(), jdbcVoucherStorage.findById(deleteVoucherId));
    }

    @Test
    @DisplayName("바우처 ID를 사용하여 특정 바우처를 찾을 수 있다.")
    void test5() {
        jdbcVoucherStorage.findById(voucherId)
                .ifPresent(findVoucher ->
                        assertThat(voucher).usingRecursiveComparison()
                                .isEqualTo(findVoucher)
                );
    }

    @Test
    @DisplayName("바우처 ID를 통하여 특정 바우처를 가진 고객 아이디 정보 가져올 수 있다.")
    void test6() {
        jdbcVoucherStorage.findById(voucherId)
                .flatMap(Voucher::getOwnerId)
                .ifPresent(findCustomerId -> assertEquals(customerId, findCustomerId));
    }

}
