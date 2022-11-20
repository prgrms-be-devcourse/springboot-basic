package org.prgrms.java.service.voucher;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.prgrms.java.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.java.*.voucher"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_mgmt")
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

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = MysqldConfig.aMysqldConfig(Version.v8_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqldConfig)
                .addSchema("voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    @AfterEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("서비스를 통해 바우처를 등록할 수 있다.")
    void testCreateVoucher() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now());

        Voucher insertedFixedAmountVoucher = voucherService.createVoucher(fixedAmountVoucher);
        Voucher insertedPercentDiscountVoucher = voucherService.createVoucher(percentDiscountVoucher);

        assertThat(fixedAmountVoucher, samePropertyValuesAs(insertedFixedAmountVoucher));
        assertThat(percentDiscountVoucher, samePropertyValuesAs(insertedPercentDiscountVoucher));
    }

    @Test
    @DisplayName("ID 같은 바우처를 둘 이상 등록할 수 없다.")
    void testCreateVouchersWithDuplicatedId() {
        UUID voucherId = UUID.randomUUID();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10000, LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, 50, LocalDateTime.now());

        Assertions.assertThrows(VoucherException.class, () -> {
            voucherService.createVoucher(fixedAmountVoucher);
            voucherService.createVoucher(percentDiscountVoucher);
        });
    }

    @Test
    @DisplayName("서비스를 통해 정상 유저를 조회할 수 있다.")
    void testGetVoucher() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now());

        voucherService.createVoucher(fixedAmountVoucher);

        assertThat(voucherService.getVoucher(fixedAmountVoucher.getVoucherId()), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 바우처를 조회하면 예외가 발생한다.")
    void testGetNonExistVoucher() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now());

        voucherService.createVoucher(fixedAmountVoucher);
        voucherService.createVoucher(percentDiscountVoucher);

        Assertions.assertThrows(VoucherException.class, () -> voucherService.getVoucher(UUID.randomUUID()));
    }

    @Test
    @DisplayName("바우처를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllVoucherWithNoCreation() {
        assertThat(voucherService.getAllVouchers(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 바우처를 조회할 수 있다.")
    void testGetAllVouchers() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now());

        voucherService.createVoucher(fixedAmountVoucher);
        voucherService.createVoucher(percentDiscountVoucher);

        assertThat(voucherService.getAllVouchers(), hasSize(2));
        assertThat(voucherService.getAllVouchers(), containsInAnyOrder(samePropertyValuesAs(fixedAmountVoucher), samePropertyValuesAs(percentDiscountVoucher)));
    }
}
