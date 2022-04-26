package com.prgrms.voucher_manager.wallet;


import com.prgrms.voucher_manager.customer.SimpleCustomer;
import com.prgrms.voucher_manager.customer.repository.JdbcCustomerRepository;
import com.prgrms.voucher_manager.voucher.FixedAmountVoucher;
import com.prgrms.voucher_manager.voucher.PercentDiscountVoucher;
import com.prgrms.voucher_manager.voucher.repository.JdbcVoucherRepository;
import com.prgrms.voucher_manager.wallet.repository.WalletRepository;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringJUnitConfig()
class WalletRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(WalletRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"com.prgrms.voucher_manager.wallet.repository",
            "com.prgrms.voucher_manager.voucher.repository",
            "com.prgrms.voucher_manager.customer.repository"
    })
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-jdbc-voucher-mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Autowired
    JdbcCustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    Wallet newWallet, newWallet2;

    EmbeddedMysql embeddedMysql;

    @BeforeEach
    void setup() {
        newWallet = new Wallet(UUID.randomUUID(), UUID.randomUUID());
        newWallet2 = new Wallet(UUID.randomUUID(), UUID.randomUUID());

        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-jdbc-voucher-mgmt", classPathScript("walletSchemaTest.sql"))
                .start();

        voucherRepository.insert(new FixedAmountVoucher(newWallet.getVoucherId(), 10));
        voucherRepository.insert(new PercentDiscountVoucher(newWallet2.getVoucherId(), 10));
        customerRepository.insert(new SimpleCustomer(newWallet.getCustomerId(), "test", "test@gmail.com", LocalDateTime.now()));
        customerRepository.insert(new SimpleCustomer(newWallet2.getCustomerId(), "test2", "test2@gmail.com", LocalDateTime.now()));
    }

    @AfterEach
    void cleanup() {
        embeddedMysql.stop();
    }



    @Test
    @DisplayName("wallet insert 테스트")
    void testInsert() {

        walletRepository.insert(newWallet);
        walletRepository.insert(newWallet2);

        List<Wallet> wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(2));
        assertThat(walletRepository.count() == 2, is(true));
    }

    @Test
    @DisplayName("이미 있는 customer-voucher 관계의 wallet insert 테스트")
    void testInsertDuplicateByEmail() {

        walletRepository.insert(newWallet);
        List<Wallet> wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(1));

        Wallet testWallet = new Wallet(newWallet.getCustomerId(), newWallet.getCustomerId());
        walletRepository.insert(testWallet);

        wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(1));
    }

    @Test
    @DisplayName("wallet 전체 조회 테스트")
    void testFindAll() {

        walletRepository.insert(newWallet);
        walletRepository.insert(newWallet2);
        List<Wallet> wallets = walletRepository.findAll();

        assertThat(wallets.isEmpty(), is(false));
        assertThat(wallets, hasSize(2));
    }

    @Test
    @DisplayName("voucher Id 를 이용해 조회 테스트")
    void testFindByVoucherId() {

        walletRepository.insert(newWallet);
        UUID voucherId = newWallet.getVoucherId();
        List<Wallet> wallets = walletRepository.findByVoucherId(voucherId);
        assertThat(wallets.isEmpty(), is(false));
        assertThat(wallets.get(0), samePropertyValuesAs(newWallet));
    }

    @Test
    @DisplayName("customer Id 를 이용해 조회 테스트")
    void testFindByCustomerId() {
        walletRepository.insert(newWallet);
        UUID customerId = newWallet.getCustomerId();
        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);
        assertThat(wallets.isEmpty(), is(false));
        assertThat(wallets.get(0), samePropertyValuesAs(newWallet));
    }


    @Test
    @DisplayName("고객이 보유한 특정 바우처를 제거할 수 있어야 한다.")
    void testDeleteByVoucherId() {

        walletRepository.insert(newWallet);
        walletRepository.insert(newWallet2);

        Wallet deleteWallet = new Wallet(newWallet.getCustomerId(), newWallet.getVoucherId());
        walletRepository.deleteByVoucherId(deleteWallet);

        List<Wallet> wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(1));
        assertThat(wallets.get(0), samePropertyValuesAs(newWallet2));

    }

}