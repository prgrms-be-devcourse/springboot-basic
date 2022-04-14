package org.prgrms.springbootbasic.repository.voucher;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqldConfig = aMysqldConfig(v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test_springboot_basic", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void clean() {
        embeddedMysql.stop();
    }

    @AfterEach
    void init() {
        jdbcVoucherRepository.removeAll();
    }

    @DisplayName("모든 바우처 조회 기능")
    @Test
    void findAll() {
        //given
        jdbcVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 100));
        jdbcVoucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 20));

        //when
        var vouchers = jdbcVoucherRepository.findAll();

        //then
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @DisplayName("모든 바우처 조회")
    @Test
    void findAllEmpty() {
        //given
        //when
        var vouchers = jdbcVoucherRepository.findAll();

        //then
        assertThat(vouchers).isEmpty();
    }

    @DisplayName("FixedAmountVoucher 저장 테스트")
    @Test
    void save() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        //when
        jdbcVoucherRepository.save(voucher);

        //then
        var foundVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(foundVoucher.isPresent()).isTrue();
        assertThat(foundVoucher.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(foundVoucher.get().getClass()).isEqualTo(voucher.getClass());
    }

    @DisplayName("PercentAmountVoucher 저장 테스트")
    @Test
    void savePercentAmountVoucher() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        //when
        jdbcVoucherRepository.save(voucher);

        //then
        var foundVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());
        assertThat(foundVoucher.isPresent()).isTrue();
        assertThat(foundVoucher.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(foundVoucher.get().getClass()).isEqualTo(voucher.getClass());
    }

    @Configuration
    @ComponentScan
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test_springboot_basic")
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
        public JdbcVoucherRepository jdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
            return new JdbcVoucherRepository(jdbcTemplate);
        }
    }
}
