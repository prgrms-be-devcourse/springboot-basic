package org.prgrms.springbootbasic.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.springbootbasic.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScripts;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.prgrms.springbootbasic.type.VoucherType.FIXED;
import static org.prgrms.springbootbasic.type.VoucherType.PERCENT;


@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DbVoucherRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = "org.prgrms.springbootbasic.repository")
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
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
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DbVoucherRepository dbVoucherRepository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher_mgmt", classPathScripts("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    public void testHikariConnectionPool() {
        System.out.println(UUID.randomUUID());
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    void testInsert() {
        UUID voucherId = UUID.randomUUID();

        Voucher insertedFixedAmountVoucher = Voucher.builder()
                .voucherId(voucherId)
                .voucherType(FIXED)
                .discountQuantity(100L)
                .discountRatio(0)
                .createdAt(LocalDate.now())
                .endedAt(LocalDate.now().plusDays(10))
                .build();

        dbVoucherRepository.insert(insertedFixedAmountVoucher);
        assertThat(insertedFixedAmountVoucher.getVoucherId(), is(voucherId));
    }


    @Test
    void testFindAll() {
        List<Voucher> allVouchers = dbVoucherRepository.findAll();

        Voucher fixed1 = Voucher.builder()
                .voucherId(UUID.fromString("67c45e69-2523-4097-8100-c8a8de3e2260"))
                .voucherType(FIXED)
                .discountQuantity(100L)
                .discountRatio(0)
                .voucherCount(1)
                .createdAt(LocalDate.of(2022, 11, 24))
                .endedAt(LocalDate.of(2022, 11, 27))
                .build();

        Voucher fixed2 = Voucher.builder()
                .voucherId(UUID.fromString("beb30b22-e81c-4a76-97e4-4a929fc28800"))
                .voucherType(FIXED)
                .discountQuantity(200L)
                .discountRatio(0)
                .voucherCount(2)
                .createdAt(LocalDate.of(2022, 11, 24))
                .endedAt(LocalDate.of(2022, 11, 28))
                .build();

        Voucher percent1 = Voucher.builder()
                .voucherId(UUID.fromString("fb90b145-8bae-46e5-8600-172fd5491588"))
                .voucherType(PERCENT)
                .discountQuantity(0)
                .discountRatio(10L)
                .voucherCount(11)
                .createdAt(LocalDate.of(2022, 11, 24))
                .endedAt(LocalDate.of(2022, 11, 24).plusDays(5))
                .build();

        Voucher percent2 = Voucher.builder()
                .voucherId(UUID.fromString("4fda8f8c-b249-4dfb-b5f4-9304d2725a97"))
                .voucherType(PERCENT)
                .discountQuantity(0)
                .discountRatio(20L)
                .voucherCount(22L)
                .createdAt(LocalDate.of(2022, 11, 24))
                .endedAt(LocalDate.of(2022, 11, 30))
                .build();

        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(fixed1);
        vouchers.add(fixed2);
        vouchers.add(percent1);
        vouchers.add(percent2);

        Assertions.assertThat(allVouchers).extracting("voucherId", "voucherType", "discountQuantity", "discountRatio", "voucherCount", "createdAt", "endedAt")
                .contains(Tuple.tuple(fixed1.getVoucherId(), fixed1.getVoucherType(), fixed1.getDiscountQuantity(), fixed1.getDiscountRatio(), fixed1.getVoucherCount(), fixed1.getCreatedAt(), fixed1.getEndedAt()))
                .contains(Tuple.tuple(fixed2.getVoucherId(), fixed2.getVoucherType(), fixed2.getDiscountQuantity(), fixed2.getDiscountRatio(), fixed2.getVoucherCount(), fixed2.getCreatedAt(), fixed2.getEndedAt()))
                .contains(Tuple.tuple(percent1.getVoucherId(), percent1.getVoucherType(), percent1.getDiscountQuantity(), percent1.getDiscountRatio(), percent1.getVoucherCount(), percent1.getCreatedAt(), percent1.getEndedAt()))
                .contains(Tuple.tuple(percent2.getVoucherId(), percent2.getVoucherType(), percent2.getDiscountQuantity(), percent2.getDiscountRatio(), percent2.getVoucherCount(), percent2.getCreatedAt(), percent2.getEndedAt()));
    }
}