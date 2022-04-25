package com.prgrms.voucher_manager.wallet;


import com.prgrms.voucher_manager.customer.SimpleCustomer;
import com.prgrms.voucher_manager.customer.repository.JdbcCustomerRepository;
import com.prgrms.voucher_manager.infra.facade.VoucherServiceFacade;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.BadSqlGrammarException;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

//    @Autowired
//    VoucherServiceFacade voucherServiceFacade;

    @Autowired
    DataSource dataSource;

    Wallet newWallet, copyWallet , sameCustomerIdWallet, sameVoucherIdWallet;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {

        newWallet = new Wallet(UUID.randomUUID(), UUID.randomUUID());
        copyWallet = new Wallet(newWallet.getCustomerId(), newWallet.getVoucherId());
        sameCustomerIdWallet = new Wallet(newWallet.getCustomerId(), UUID.randomUUID());
        sameVoucherIdWallet = new Wallet(UUID.randomUUID(), newWallet.getVoucherId());

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
        voucherRepository.insert(new PercentDiscountVoucher(sameCustomerIdWallet.getVoucherId(), 20));

        customerRepository.insert(new SimpleCustomer(newWallet.getCustomerId(), "beomsic", "test@gmail.com", LocalDateTime.now()));
        customerRepository.insert(new SimpleCustomer(sameVoucherIdWallet.getCustomerId(), "beomsic2", "test2@gmail.com", LocalDateTime.now()));

    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("wallet insert 테스트")
    void testInsert() {
        try {
            walletRepository.insert(newWallet);
            walletRepository.insert(sameCustomerIdWallet);
            walletRepository.insert(sameVoucherIdWallet);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        }

        assertThat(walletRepository.count() == 3, is(true));
    }

    @Test
    @Order(3)
    @Disabled
    @DisplayName("이미 있는 customer-voucher 관계의 wallet insert 테스트")
    void testInsertDuplicateByEmail() {
        try {
            walletRepository.insert(copyWallet);
        } catch (BadSqlGrammarException e) {
            logger.error("testInsert - BadSqlGrammarException -> {}",e.getSQLException().getErrorCode());
        }
        List<Wallet> wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(3));
    }

    @Test
    @Order(4)
    @DisplayName("walletRepository 에 들어있는 customer 수 조회 테스트")
    void testCountWalletRepository() {
        assertThat(walletRepository.count() == 3, is(true));
        System.out.println(walletRepository.count());
    }


    @Test
    @Order(4)
    @DisplayName("wallet 전체 조회 테스트")
    void testFindAll() {
        List<Wallet> wallets = walletRepository.findAll();
        wallets.forEach(w -> {
            System.out.println(w.toString());
        });
        assertThat(wallets.isEmpty(), is(false));
        assertThat(wallets, hasSize(3));

    }

    @Test
    @Order(4)
    @DisplayName("voucher Id 를 이용해 조회 테스트")
    void testFindByVoucherId() {
        UUID voucherId = newWallet.getVoucherId();
        List<Wallet> wallets = walletRepository.findByVoucherId(voucherId);
        assertThat(wallets.isEmpty(), is(false));
        assertThat(wallets,hasSize(2));
        wallets.forEach(e -> {
            System.out.println(e.toString());
            assertThat(e, anyOf(
                    samePropertyValuesAs(newWallet),
                    samePropertyValuesAs(sameVoucherIdWallet)
            ));
        });
    }

    @Test
    @Order(4)
    @DisplayName("customer Id 를 이용해 조회 테스트")
    void testFindByCustomerId() {
        UUID customerId = newWallet.getCustomerId();
        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);
        assertThat(wallets.isEmpty(), is(false));
        assertThat(wallets,hasSize(2));
        wallets.forEach(e -> {
            assertThat(e, anyOf(
                    samePropertyValuesAs(newWallet),
                    samePropertyValuesAs(sameCustomerIdWallet)
            ));
        });
    }

    @Test
    @Order(5)
    @DisplayName("고객의 특정 voucher를 다른 voucher로 수정하는 테스트")
    void testUpdateCustomer() {

        FixedAmountVoucher otherVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1500);
        voucherRepository.insert(otherVoucher);

        UUID voucherId = newWallet.getVoucherId();
        newWallet.changeVoucher(otherVoucher);
        walletRepository.update(newWallet, voucherId);

        List<Wallet> wallets = walletRepository.findAll();

        assertThat(wallets, hasSize(3));

        List<Wallet> updateWallets = walletRepository.findByCustomerId(newWallet.getCustomerId());
        System.out.println(sameCustomerIdWallet.getVoucherId());
        for (Wallet updateWallet : updateWallets) {
            System.out.println(updateWallet.getVoucherId());
        }
        assertThat(updateWallets.isEmpty(),is(false));
        assertThat(updateWallets, hasSize(2));

    }


    @Test
    @Order(6)
    @DisplayName("고객이 보유한 특정 바우처를 제거할 수 있어야 한다.")
    void testDeleteByVoucherId() {

        List<Wallet> wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(3));

        UUID voucherId = newWallet.getVoucherId();
        Wallet deleteWallet = new Wallet(newWallet.getCustomerId(), voucherId);
        walletRepository.deleteByVoucherId(deleteWallet);

        wallets = walletRepository.findAll();
        assertThat(wallets, hasSize(1));

    }

}