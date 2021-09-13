package org.prgrms.kdt.repository.wallet;

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
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.model.wallet.Wallet;
import org.prgrms.kdt.repository.customer.CustomerJdbcRepository;
import org.prgrms.kdt.repository.voucher.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class WalletJdbcRepositoryTest {

    private EmbeddedMysql embeddedMysql;
    private Wallet wallet;
    private Customer customer;
    private Voucher voucher;

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
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    WalletRepository walletJdbcRepository;

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
    }

    @Test
    @DisplayName("고객에게 바우처를 할당할 수 있다.")
    @Order(1)
    void insert() {
        customer = customerJdbcRepository.insert(
            new Customer(
                UUID.randomUUID(),
                "test-customer",
                "test@gmail.com",
                LocalDateTime.now()
            ));
        voucher = voucherJdbcRepository.insert(
            new Voucher(
                UUID.randomUUID(),
                1000,
                LocalDateTime.now(),
                VoucherType.FIX
            )
        );
        wallet = new Wallet(
            UUID.randomUUID(),
            customer.getCustomerId(),
            voucher.getVoucherId(),
            LocalDateTime.now());
        var retrievedWallet = walletJdbcRepository.insert(wallet);
        assertThat(retrievedWallet, samePropertyValuesAs(wallet));
    }

    @Test
    @DisplayName("고객의 아이디로 고객의 바우처를 조회할 수 있다.")
    @Order(2)
    void findByCustomerId() {
        var vouchers = voucherJdbcRepository.findByCustomerId(wallet.getCustomerId());
        assertThat(vouchers.size(), is(1));
        assertThat(vouchers, everyItem(samePropertyValuesAs(voucher)));
    }

    @Test
    @DisplayName("바우처 아이디로 고객 조회 테스트")
    @Order(3)
    void findByVoucherId() {
        var customers = customerJdbcRepository.findByVoucherId(wallet.getVoucherId());
        assertThat(customers.size(), is(1));
        assertThat(customers, everyItem(samePropertyValuesAs(customer)));
    }

    @Test
    @DisplayName("고객에게 등록된 바우처를 삭제할 수 있다.")
    @Order(4)
    void deleteByWallet() {
        walletJdbcRepository.deleteByCustomerVoucher(wallet.getCustomerId(), wallet.getVoucherId());
        var vouchers = voucherJdbcRepository.findByCustomerId(wallet.getCustomerId());
        assertThat(vouchers.isEmpty(), is(true));
    }


}