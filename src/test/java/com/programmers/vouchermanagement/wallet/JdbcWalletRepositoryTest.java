package com.programmers.vouchermanagement.wallet;

import com.programmers.vouchermanagement.global.common.JdbcRepositoryManager;
import com.programmers.vouchermanagement.wallet.domain.Wallet;
import com.programmers.vouchermanagement.wallet.repository.JdbcWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JdbcWalletRepositoryTest {

    @Autowired
    private JdbcWalletRepository walletRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, index) -> {

        UUID walletId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("wallet_id"));
        UUID customerId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("voucher_id"));

        return new Wallet(walletId, customerId, voucherId);
    };

    @BeforeEach
    void clear() {
        jdbcTemplate.update("DELETE FROM wallet");
    }

    @Test
    @DisplayName("Wallet을 저장할 수 있다.")
    void successSave() {

        // given
        Wallet wallet = new Wallet(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        // when
        walletRepository.save(wallet);

        // then
        Wallet savedWallet = jdbcTemplate.queryForObject("SELECT * FROM wallet WHERE wallet_id = UUID_TO_BIN(?)",
                walletRowMapper,
                wallet.getWalletId().toString());
        assertThat(savedWallet.getWalletId()).isEqualTo(wallet.getWalletId());
        assertThat(savedWallet.getCustomerId()).isEqualTo(wallet.getCustomerId());
        assertThat(savedWallet.getVoucherId()).isEqualTo(wallet.getVoucherId());
    }

    @Test
    @DisplayName("고객 아이디로 Wallet 목록을 조회할 수 있다.")
    void successFindByCustomerId() {

        // given
        UUID customerId = UUID.randomUUID();
        Wallet wallet1 = new Wallet(UUID.randomUUID(), customerId, UUID.randomUUID());
        Wallet wallet2 = new Wallet(UUID.randomUUID(), customerId, UUID.randomUUID());
        Wallet wallet3 = new Wallet(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        // when
        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);

        // then
        assertThat(wallets).hasSize(2)
                .extracting(Wallet::getWalletId)
                .contains(wallet1.getWalletId(), wallet2.getWalletId());
    }

    @Test
    @DisplayName("바우처 아이디로 Wallet 목록을 조회할 수 있다.")
    void successFindByVoucherId() {

        // given
        UUID voucherId = UUID.randomUUID();
        Wallet wallet1 = new Wallet(UUID.randomUUID(), UUID.randomUUID(), voucherId);
        Wallet wallet2 = new Wallet(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Wallet wallet3 = new Wallet(UUID.randomUUID(), UUID.randomUUID(), voucherId);
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        // when
        List<Wallet> wallets = walletRepository.findByVoucherId(voucherId);

        // then
        assertThat(wallets).hasSize(2)
                .extracting(Wallet::getWalletId)
                .contains(wallet1.getWalletId(), wallet3.getWalletId());
    }

    @Test
    @DisplayName("고객 아이디로 Wallet을 삭제할 수 있다.")
    void successDeleteByCustomerId() {

        // given
        UUID customerId = UUID.randomUUID();
        UUID voucherId = UUID.randomUUID();
        Wallet wallet1 = new Wallet(UUID.randomUUID(), customerId, UUID.randomUUID());
        Wallet wallet2 = new Wallet(UUID.randomUUID(), customerId, voucherId);
        Wallet wallet3 = new Wallet(UUID.randomUUID(), UUID.randomUUID(), voucherId);
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        // when
        walletRepository.deleteByCustomerId(customerId);

        // then
        List<Wallet> walletsByCustomer = walletRepository.findByCustomerId(customerId);
        assertThat(walletsByCustomer).hasSize(0);
        List<Wallet> walletsByVoucher = walletRepository.findByVoucherId(voucherId);
        assertThat(walletsByVoucher).hasSize(1)
                .extracting(Wallet::getWalletId)
                .contains(wallet3.getWalletId());
    }
}
