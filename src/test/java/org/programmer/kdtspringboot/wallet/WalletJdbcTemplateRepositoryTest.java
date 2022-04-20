package org.programmer.kdtspringboot.wallet;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmer.kdtspringboot.customer.Customer;
import org.programmer.kdtspringboot.customer.CustomerJdbcTemplateRepository;
import org.programmer.kdtspringboot.voucher.FixedAmountVoucher;
import org.programmer.kdtspringboot.voucher.PercentDiscountVoucher;
import org.programmer.kdtspringboot.voucher.Voucher;
import org.programmer.kdtspringboot.voucher.VoucherJdbcTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletJdbcTemplateRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.programmer.kdtspringboot.wallet",
            "org.programmer.kdtspringboot.customer",
            "org.programmer.kdtspringboot.voucher"
    })
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    WalletJdbcTemplateRepository walletJdbcTemplateRepository;

    @Autowired
    CustomerJdbcTemplateRepository customerJdbcTemplateRepository;

    @Autowired
    VoucherJdbcTemplateRepository voucherJdbcTemplateRepository;

    @Autowired
    DataSource dataSource;

    Wallet wallet;
    Wallet wallet2;
    Customer customer;
    Customer customer2;
    Voucher fixVoucher;
    Voucher percentVoucher;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        //임베디드 실습
        customer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customer2 = new Customer(UUID.randomUUID(), "test-user2", "test-kim@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        fixVoucher = new FixedAmountVoucher(UUID.randomUUID(),10L);
        percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(),20L);
        wallet = new Wallet(UUID.randomUUID(),customer.getCustomerId(),fixVoucher.getVoucherId());
        wallet2 = new Wallet(UUID.randomUUID(),customer2.getCustomerId(),percentVoucher.getVoucherId());
        var mysqlConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();

        voucherJdbcTemplateRepository.insert(fixVoucher);
        voucherJdbcTemplateRepository.insert(percentVoucher);

        customerJdbcTemplateRepository.insert(customer);
        customerJdbcTemplateRepository.insert(customer2);
    }

    @AfterAll
    void tearDown() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("바우처 지갑 만들기")
    void insertWalletTest() {
        walletJdbcTemplateRepository.insert(wallet);
        walletJdbcTemplateRepository.insert(wallet2);
        var retrievedWallet = walletJdbcTemplateRepository.findByWalletId(wallet.getWalletId());
        assertThat(retrievedWallet).isNotEmpty();
        assertThat(retrievedWallet.get()).usingRecursiveComparison().isEqualTo(wallet);
    }

    @Test
    @Order(2)
    @DisplayName("전체 바우처 지갑을 조회할 수 있음")
    void findAllWalletTest() {
        var wallets = walletJdbcTemplateRepository.findAll();
        assertThat(wallets).isNotEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("특정 바우처를 가진 고객을 조회할 수 있음")
    void findVoucherIdWalletTest() {
        var wallets =walletJdbcTemplateRepository.findByVoucherId(fixVoucher.getVoucherId());
        assertThat(wallets).isNotEmpty();

        var wallets2 = walletJdbcTemplateRepository.findByVoucherId(UUID.randomUUID());
        assertThat(wallets2).isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("특정 고객이 가진 바우처를 조회할 수 있음")
    void findCustomerIdWalletTest() {
        var wallets =walletJdbcTemplateRepository.findByCustomerId(customer.getCustomerId());
        assertThat(wallets).isNotEmpty();

        var wallets2 = walletJdbcTemplateRepository.findByCustomerId(UUID.randomUUID());
        assertThat(wallets2).isEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("특정 바우처를 가진 지갑을 삭제 할 수 있음")
    void deleteVoucherIdWalletTest() {
        var wallets =walletJdbcTemplateRepository.findAll();
        assertThat(wallets).isNotEmpty();
        assertThat(wallets).hasSize(2);

        walletJdbcTemplateRepository.deleteVoucher(fixVoucher.getVoucherId());

        var retrievedWallet = walletJdbcTemplateRepository.findByVoucherId(fixVoucher.getVoucherId());
        assertThat(retrievedWallet).isEmpty();

    }
}