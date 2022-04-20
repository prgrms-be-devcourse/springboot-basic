package com.voucher.vouchermanagement.service;


import com.voucher.vouchermanagement.configuration.YamlPropertiesFactory;
import com.voucher.vouchermanagement.model.voucher.FixedAmountVoucher;
import com.voucher.vouchermanagement.model.voucher.PercentDiscountVoucher;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.repository.voucher.VoucherJdbcRepository;
import com.voucher.vouchermanagement.repository.voucher.VoucherRepository;
import com.voucher.vouchermanagement.service.voucher.VoucherService;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = {"com.voucher.vouchermanagement.service.voucher",
            "com.voucher.vouchermanagement.repository.voucher"})
    @PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
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

        @Bean
        VoucherRepository voucherRepository(JdbcTemplate jdbcTemplate) {
            return new VoucherJdbcRepository(jdbcTemplate);
        }

        @Bean
        PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @AfterEach
    public void cleanEach() {
        voucherRepository.deleteAll();
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

    @AfterAll
    public void stopDb() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("다건 추가 테스트")
    public void multiInsertVoucherTest() {
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now()),
                new FixedAmountVoucher(UUID.randomUUID(), 20L, LocalDateTime.now()),
                new PercentDiscountVoucher(UUID.randomUUID(), 30L, LocalDateTime.now()),
                new PercentDiscountVoucher(UUID.randomUUID(), 40L, LocalDateTime.now())
        );

        voucherService.createVouchers(vouchers);

        List<Voucher> foundVouchers = voucherService.findAll();

        assertThat(foundVouchers.size(), is(4));
        assertThat(foundVouchers, containsInAnyOrder(samePropertyValuesAs(foundVouchers.get(0)),
                samePropertyValuesAs(foundVouchers.get(1)),
                samePropertyValuesAs(foundVouchers.get(2)),
                samePropertyValuesAs(foundVouchers.get(3))
        ));
    }

    @Test
    @DisplayName("다건 추가 실패시 전체 트랙잭션이 롤백 되어야한다.")
    public void multiInsertVoucherRollbackTest() {
        UUID duplicatedUUID = UUID.randomUUID();
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(duplicatedUUID, 10L, LocalDateTime.now()),
                new FixedAmountVoucher(duplicatedUUID, 20L, LocalDateTime.now())
        );

        try {
            voucherService.createVouchers(vouchers);
        } catch (DataAccessException e) {
        }
        List<Voucher> foundVouchers = voucherService.findAll();

        assertThat(foundVouchers.size(), is(0));
        assertThat(foundVouchers.isEmpty(), is(true));
        assertThat(foundVouchers, not(containsInAnyOrder(samePropertyValuesAs(vouchers.get(0)),
                samePropertyValuesAs(vouchers.get(1))
        )));
    }

    @Test
    @DisplayName("100% 초과 PercentDiscountVoucher 생성 불가 테스트 - 101%")
    public void createPercentDiscountVoucherByOverMaxLimitValueTest() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(VoucherType.Percent, 101L));
    }

    @Test
    @DisplayName("1% 미만 PercentDiscountVoucher 생성 불가 테스트 - 0%")
    public void createPercentDiscountVoucherByUnderMaxLimitValueTest() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(VoucherType.Percent, 0L));
    }

    @Test
    @DisplayName("PercentDiscountVoucher의 최소 할인 비율은 1퍼센트다.")
    public void createPercentDiscountVoucherTest_1() {
        voucherService.createVoucher(VoucherType.Percent, 1L);

        List<Voucher> foundVoucher = voucherService.findAll();

        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(1L));
        assertThat(foundVoucher.get(0).getClass().getSimpleName(), is(PercentDiscountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("100% PercentDiscountVoucher 생성 테스트")
    public void createPercentDiscountVoucherTest_100() {
        voucherService.createVoucher(VoucherType.Percent, 100L);

        List<Voucher> foundVoucher = voucherService.findAll();

        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(100L));
        assertThat(foundVoucher.get(0).getClass().getSimpleName(), is(PercentDiscountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("FixedAmountVoucher의 최소 할인 금액은 1원이다.")
    public void createFixedAmountVoucherTest_1() {
        voucherService.createVoucher(VoucherType.Fixed, 1L);

        List<Voucher> foundVoucher = voucherService.findAll();

        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(1L));
        assertThat(foundVoucher.get(0).getClass().getSimpleName(), is(FixedAmountVoucher.class.getSimpleName()));
    }

    @Test
    @DisplayName("할인금액 1미만 FixedAmountVoucher 생성 불가 테스트 - 0")
    public void createFixedAmountVoucherByUnderMaxLimitValueTest() {
        assertThrows(IllegalArgumentException.class, () -> voucherService.createVoucher(VoucherType.Fixed, 0L));
    }

    @Test
    @DisplayName("5000원 할인 FixedAmountVoucher 생성 테스트")
    public void createFixedAmountVoucherTest_5000() {
        voucherService.createVoucher(VoucherType.Fixed, 5000L);

        List<Voucher> foundVoucher = voucherService.findAll();

        assertThat(foundVoucher.size(), is(1));
        assertThat(foundVoucher.get(0).getValue(), is(5000L));
        assertThat(foundVoucher.get(0).getClass().getSimpleName(), is(FixedAmountVoucher.class.getSimpleName()));
    }


}
