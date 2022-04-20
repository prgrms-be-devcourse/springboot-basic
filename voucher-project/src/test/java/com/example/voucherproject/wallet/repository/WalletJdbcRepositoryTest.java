package com.example.voucherproject.wallet.repository;

import com.example.voucherproject.wallet.domain.Wallet;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
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
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Wallet JDBC REPO TEST")
class WalletJdbcRepositoryTest {

    @Autowired
    WalletJdbcRepository walletJdbcRepository;

    EmbeddedMysql embeddedMysql;

    @Test
    @Order(1)
    @DisplayName("insert")
    void insertTest() {
        var wallet = makeWallet();
        walletJdbcRepository.insert(wallet);
        assertThat(walletJdbcRepository.count()).isEqualTo(1);
        walletJdbcRepository.deleteAll();
    }

    @Test
    @Order(2)
    @DisplayName("find all")
    void findAllTest() {
        var wallet1 = walletJdbcRepository.insert(makeWallet());
        var wallet2 = walletJdbcRepository.insert(makeWallet());
        var wallet3 = walletJdbcRepository.insert(makeWallet());

        var wallets = walletJdbcRepository.findAll();
        assertThat(wallets).usingRecursiveFieldByFieldElementComparator()
                .contains(wallet1, wallet2, wallet3);
        walletJdbcRepository.deleteAll();
    }

    @Test
    @Order(3)
    @DisplayName("find by userId and voucherId")
    void findByIdsTest() {
        UUID userId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        var wallet = makeWalletTwoIds(userId, voucherId);
        walletJdbcRepository.insert(wallet);

        var findWallet = walletJdbcRepository.findByIds(userId, voucherId);

        assertThat(findWallet.get()).usingRecursiveComparison().isEqualTo(wallet);
        walletJdbcRepository.deleteAll();
    }

    @Test
    @Order(4)
    @DisplayName("delete by id")
    void deleteByIdTest() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();

        var wallet = makeWalletThreeIds(id, userId, voucherId);
        walletJdbcRepository.insert(wallet);

        walletJdbcRepository.deleteById(id);
        var wallets = walletJdbcRepository.findAll();

        assertThat(wallets).usingRecursiveFieldByFieldElementComparator().doesNotContain(wallet);
        walletJdbcRepository.deleteAll();
    }


    @Test
    @Order(5)
    @DisplayName("delete by user id")
    void findByUserIdTest() {
        walletJdbcRepository.insert(makeWallet());
        walletJdbcRepository.insert(makeWallet());
        walletJdbcRepository.insert(makeWallet());

        UUID userId = UUID.randomUUID();
        var walletTarget1 = makeWalletTwoIds(userId, UUID.randomUUID());
        var walletTarget2 = makeWalletTwoIds(userId, UUID.randomUUID());

        walletJdbcRepository.insert(walletTarget1);
        walletJdbcRepository.insert(walletTarget2);

        var searchedWallets = walletJdbcRepository.findByUserId(userId);

        assertThat(searchedWallets).usingRecursiveFieldByFieldElementComparator()
                .contains(walletTarget1, walletTarget2);

        walletJdbcRepository.deleteAll();
    }

    private Wallet makeWallet(){
        return new Wallet(UUID.randomUUID(),UUID.randomUUID(), UUID.randomUUID(),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
    private Wallet makeWalletTwoIds(UUID userId, UUID voucherId){
        return new Wallet(UUID.randomUUID(),userId, voucherId,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
    private Wallet makeWalletThreeIds(UUID id, UUID userId, UUID voucherId){
        return new Wallet(id, userId, voucherId,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }


    @BeforeAll
    void setup() {
        System.out.println("before al");
        var config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("voucher_app", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup(){
        embeddedMysql.stop();
    }



    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            var source = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_app")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            source.setMaximumPoolSize(1000);
            source.setMinimumIdle(100);
            return source;
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public WalletJdbcRepository walletJdbcRepository(JdbcTemplate jdbcTemplate){
            return new WalletJdbcRepository(jdbcTemplate);
        }
    }

}