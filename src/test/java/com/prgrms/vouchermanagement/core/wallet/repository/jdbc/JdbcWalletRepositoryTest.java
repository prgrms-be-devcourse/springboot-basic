package com.prgrms.vouchermanagement.core.wallet.repository.jdbc;

import com.prgrms.vouchermanagement.core.wallet.domain.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = {JdbcWalletRepository.class, JdbcWalletRepositoryTest.config.class})
class JdbcWalletRepositoryTest {

    @Configuration
    static class config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("sql/wallet-init.sql")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private JdbcWalletRepository jdbcWalletRepository;

    @BeforeEach
    public void cleanUp() {
        jdbcWalletRepository.deleteAll();
    }

    @DisplayName("Wallet을 저장할 수 있다.")
    @Test
    void testSave() {
        // given
        String id = UUID.randomUUID().toString();
        String customerId = UUID.randomUUID().toString();
        String voucherId = UUID.randomUUID().toString();
        Wallet wallet = new Wallet(id, customerId, voucherId);

        // when
        Wallet savedWallet = jdbcWalletRepository.save(wallet);

        // then
        assertThat(savedWallet).isEqualTo(wallet);
    }

    @DisplayName("Wallet을 모두 조회할 수 있다.")
    @Test
    void testFindAll() {
        // given
        Wallet wallet1 = new Wallet(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        Wallet wallet2 = new Wallet(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        jdbcWalletRepository.save(wallet1);
        jdbcWalletRepository.save(wallet2);

        // when
        List<Wallet> walletList = jdbcWalletRepository.findAll();

        // then
        assertThat(walletList.containsAll(List.of(wallet1, wallet2)));
    }

    @DisplayName("Customer가 보유한 Voucher들을 삭제할 수 있다.")
    @Test
    void testDeleteAllByCustomerId() {
        // given
        String customerId = UUID.randomUUID().toString();
        Wallet wallet1 = new Wallet(UUID.randomUUID().toString(), customerId, UUID.randomUUID().toString());
        Wallet wallet2 = new Wallet(UUID.randomUUID().toString(), customerId, UUID.randomUUID().toString());
        Wallet wallet3 = new Wallet(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        jdbcWalletRepository.save(wallet1);
        jdbcWalletRepository.save(wallet2);
        jdbcWalletRepository.save(wallet3);

        // when
        jdbcWalletRepository.deleteAllByCustomerId(customerId);
        List<Wallet> walletList = jdbcWalletRepository.findAll();

        // then
        assertAll(
                () -> assertThat(walletList).hasSize(1),
                () -> assertThat(walletList.get(0)).isEqualTo(wallet3)
        );
    }
}
