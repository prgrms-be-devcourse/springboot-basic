package org.prgrms.kdt.repository.voucher;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

    private EmbeddedMysql embeddedMysql;
    private Voucher fixedVoucher;
    private Voucher percentVoucher;

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-command_application")
                .username("test").password("test1234!").type(HikariDataSource.class).build();
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

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;


    @Autowired
    DataSource dataSource;

    @BeforeAll
    void setUp() {
        MysqldConfig config = aMysqldConfig(v8_0_11)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .withTimeout(2, TimeUnit.MINUTES)
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-command_application", classPathScript("schema.sql"))
            .start();

        fixedVoucher = new Voucher(UUID.randomUUID(), 1000, LocalDateTime.now(), VoucherType.FIX);
        percentVoucher = new Voucher(UUID.randomUUID(), 50, LocalDateTime.now(), VoucherType.PERCENT);
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("바우처를 추가할 수 있다.")
    @Order(2)
    void testInsert() {
        voucherJdbcRepository.insert(fixedVoucher);
        var retrievedFixedVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());
        assertThat(retrievedFixedVoucher.isEmpty(), is(false));
        assertThat(retrievedFixedVoucher.get(), samePropertyValuesAs(fixedVoucher));


        voucherJdbcRepository.insert(percentVoucher);
        var retrievedPercentVoucher = voucherJdbcRepository.findById(percentVoucher.getVoucherId());
        assertThat(retrievedPercentVoucher.isEmpty(), is(false));
        assertThat(retrievedPercentVoucher.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @DisplayName("전체 바우처를 조회할 수 있다.")
    @Order(3)
    void testFindAll() {
        var vouchers = voucherJdbcRepository.findAllVoucher();
        assertThat(vouchers.size(), is(2));
    }

    @Test
    @DisplayName("아이디로 바우처를 조회할 수 있다.")
    @Order(4)
    void testFindById() {
        var retrievedVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));

        var unknown = voucherJdbcRepository.findById(UUID.randomUUID());
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처의 타입과 값을 변경할 수 있다.")
    @Order(5)
    void testVoucherUpdate() {
        var newType = VoucherType.PERCENT;
        var newDiscountAmount = 40L;

        fixedVoucher.changeVoucherType(newType, newDiscountAmount);
        voucherJdbcRepository.updateType(fixedVoucher);

        var retrievedVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId()).get();
        assertThat(retrievedVoucher.getVoucherType(), is(newType));
        assertThat(retrievedVoucher.getDiscount(), is(newDiscountAmount));
    }

}
