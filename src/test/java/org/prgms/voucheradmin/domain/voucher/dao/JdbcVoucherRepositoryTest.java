package org.prgms.voucheradmin.domain.voucher.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcVoucherRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgms.voucheradmin"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var datasource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher-admin")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();

            return datasource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    VoucherRepository jdbcVoucherRepository;

    EmbeddedMysql embeddedMysql;

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

    @BeforeAll
    void setUp() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher-admin", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처 생성 확인")
    void testCreationVoucher() throws Throwable{
        jdbcVoucherRepository.create(voucher);

        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers.size(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 수정 확인")
    void testUpdateVoucher() throws Throwable{
        jdbcVoucherRepository.update(new PercentageDiscountVoucher(voucher.getVoucherId(), 20));

        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers.get(0).getAmount(), is(20L));
        assertThat(vouchers.get(0).getVoucherType(), is(PERCENTAGE_DISCOUNT));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 id 조회 확인")
    void testFindById() throws Throwable{
        Optional<Voucher> retrievedVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId());

        assertThat(retrievedVoucher, not(is(Optional.empty())));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 삭제 확인")
    void testDeleteById() throws Throwable{
        jdbcVoucherRepository.delete(voucher);

        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers.size(), is(0));
    }
}