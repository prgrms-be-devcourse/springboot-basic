package org.prgrms.dev.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "dev")
class VoucherRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeAll
    static void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test_springboot_order", classPathScript("schema.sql"), classPathScript("data.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("바우처를 생성할 수 있다.")
    @Test
    void createTest() {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50L, LocalDateTime.now());
        voucherRepository.insert(voucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(retrievedVoucher).isNotEmpty();
    }

    @DisplayName("바우처의 할인정보를 수정할 수 있다.")
    @Test
    void updateDiscountValueTest() {
        UUID voucherId = UUID.fromString("6b20f733-628c-431e-b8ae-d76b81175554");
        Voucher voucher = new PercentDiscountVoucher(voucherId, 20L, LocalDateTime.now());

        voucherRepository.update(voucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(20L);
    }

    @DisplayName("바우처를 삭제할 수 있다.")
    @Test
    void deleteVoucherTest() {
        UUID voucherId = UUID.fromString("c9cf01c2-fbd1-4dfa-86d3-46c745892e60");
        voucherRepository.deleteById(voucherId);

        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);
        assertThat(retrievedVoucher).isEmpty();
    }

    @DisplayName("아이디로 원하는 바우처를 조회할 수 있다.")
    @Test
    void findByIdTest() {
        UUID voucherId = UUID.fromString("5129a45c-6ec7-4158-8be0-9f24c3c110f1");
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucherId);

        assertThat(retrievedVoucher).isNotEmpty();
        assertThat(retrievedVoucher.get().getVoucherType()).isEqualTo(VoucherType.FIXED);
    }

    @DisplayName("존재하지 않는 아이디로 조회시 Optional.Empty 반환")
    @Test
    void findByNoIdTest() {
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(UUID.randomUUID());

        assertThat(retrievedVoucher).isEmpty();
    }

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.dev.voucher"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_springboot_order")
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
}