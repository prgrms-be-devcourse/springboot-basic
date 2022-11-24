package org.prgrms.java.repository.voucher;

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
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {
    private static final DataSource dataSource = DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
            .username("test")
            .password("test1234!")
            .type(HikariDataSource.class)
            .build();
    private static final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    private static final VoucherRepository voucherRepository = new JdbcVoucherRepository(namedParameterJdbcTemplate);

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
                .addSchema("test-voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 등록할 수 있다.")
    void testInsert() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());

        Voucher insertedFixedAmountVoucher = voucherRepository.insert(fixedAmountVoucher);

        assertThat(fixedAmountVoucher, samePropertyValuesAs(insertedFixedAmountVoucher));
    }

    @Test
    @DisplayName("동일한 ID의 바우처는 등록할 수 없다.")
    void testInsertSameIdVoucher() {
        assertThrows(VoucherException.class, () -> {
            UUID voucherId = UUID.randomUUID();
            Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 100, LocalDateTime.now(), LocalDateTime.now());
            Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, 10, LocalDateTime.now(), LocalDateTime.now());

            voucherRepository.insert(fixedAmountVoucher);
            voucherRepository.insert(percentDiscountVoucher);
        });
    }

    @Test
    @DisplayName("등록한 바우처를 ID, 소유자 ID로 찾을 수 있다.")
    void testFindById() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now(), LocalDateTime.now());
        UUID ownerId = UUID.randomUUID();
        percentDiscountVoucher.setOwnerId(ownerId);

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get(), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(voucherRepository.findByCustomer(ownerId), hasSize(1));
        assertThat(voucherRepository.findByCustomer(ownerId).get(0), samePropertyValuesAs(percentDiscountVoucher));
        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get(), not(samePropertyValuesAs((percentDiscountVoucher))));
    }

    @Test
    @DisplayName("등록한 바우처와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findAll().isEmpty(), is(false));
        assertThat(voucherRepository.findAll(), hasSize(2));
    }

    @Test
    @DisplayName("등록한 개수와 전체 삭제한 개수가 같다.")
    void testDeleteAll() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.deleteAll(), is(2L));
    }
}