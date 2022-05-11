package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.FixedAmountVoucher;
import com.voucher.vouchermanagement.model.voucher.PercentDiscountVoucher;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(basePackages = {"com.voucher.vouchermanagement.repository.voucher"})
    @AutoConfigureJdbc
    static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }
    }

    @BeforeAll
    public void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @AfterEach
    public void cleanEach() {
        voucherRepository.deleteAll();
    }

    @AfterAll
    public void stopDb() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("FixedAmountVoucher 생성 테스트")
    public void insertFixedAmountVoucherTest() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 20L, LocalDateTime.now());

        //when
        voucherRepository.insert(voucher);
        List<Voucher> foundVouchers = voucherRepository.findAll();

        //then
        assertThat(foundVouchers.size(), is(1));
        assertThat(foundVouchers.get(0).getClass().getSimpleName(), is(FixedAmountVoucher.class.getSimpleName()));
        assertThat(foundVouchers.get(0), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 테스트")
    public void insertPercentDiscountTest() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());

        //when
        voucherRepository.insert(voucher);
        List<Voucher> foundVouchers = voucherRepository.findAll();

        //then
        assertThat(foundVouchers.size(), is(1));
        assertThat(foundVouchers.get(0).getClass().getSimpleName(), is(PercentDiscountVoucher.class.getSimpleName()));
        assertThat(foundVouchers.get(0), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("Voucher 전체 조회 테스트")
    public void findAllVouchersTest() {
        //given
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now()),
                new FixedAmountVoucher(UUID.randomUUID(), 20L, LocalDateTime.now()),
                new PercentDiscountVoucher(UUID.randomUUID(), 30L, LocalDateTime.now()),
                new PercentDiscountVoucher(UUID.randomUUID(), 40L, LocalDateTime.now())
        );
        vouchers.forEach(voucherRepository::insert);

        //when
        List<Voucher> foundVouchers = voucherRepository.findAll();

        //then
        assertThat(foundVouchers, containsInAnyOrder(samePropertyValuesAs(vouchers.get(0)),
                samePropertyValuesAs(vouchers.get(1)),
                samePropertyValuesAs(vouchers.get(2)),
                samePropertyValuesAs(vouchers.get(3))
        ));
    }

    @Test
    public void findByIdTest() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
        voucherRepository.insert(voucher);

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(foundVoucher.isPresent(), is(true));
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }
}