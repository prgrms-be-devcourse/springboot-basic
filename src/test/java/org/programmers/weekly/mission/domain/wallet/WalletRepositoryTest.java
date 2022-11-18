package org.programmers.weekly.mission.domain.wallet;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.weekly.mission.domain.customer.model.Customer;
import org.programmers.weekly.mission.domain.customer.repository.CustomerJdbcRepository;
import org.programmers.weekly.mission.domain.voucher.model.FixedAmountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.repository.VoucherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.weekly.mission.domain"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher_mgmt")
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

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    CustomerJdbcRepository customerJdbcRepository;
    @Autowired
    VoucherJdbcRepository voucherRepository;

    @Autowired
    DataSource dataSource;

    Wallet newWallet;
    Customer newCustomer;
    Voucher newVoucher;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());
        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        newWallet = new Wallet(UUID.randomUUID(), newCustomer.getCustomerId(), newVoucher.getVoucherId());

        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_voucher_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsertCustomer() {
        customerJdbcRepository.insert(newCustomer);

        Optional<Customer> retrievedCustomer = customerJdbcRepository.getCustomerById(newCustomer.getCustomerId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("바우처를 추가할 수 있다")
    public void testInsertVoucher() {
        Voucher insert = voucherRepository.insert(newVoucher);

        Optional<Voucher> receivedVoucher = voucherRepository.findById(newVoucher.getVoucherId());

        assertThat(receivedVoucher.isEmpty(), is(false));
        assertThat(receivedVoucher.get(), samePropertyValuesAs(insert));
    }

    @Test
    @DisplayName("특정 고객에게 바우처를 할당할 수 있다")
    @Order(4)
    public void testInsert() {
        Wallet wallet = walletRepository.insert(newWallet);
        assertThat(wallet, samePropertyValuesAs(newWallet));
    }

    @Test
    @DisplayName("고객이 어떤 바우처를 보유하고 있는 지 조회할 수 있다")
    @Order(5)
    public void testFindVouchersByCustomerId() {
        List<Voucher> vouchers = walletRepository.findVoucherByCustomerId(newCustomer.getCustomerId());
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers, hasSize(1));
    }


}