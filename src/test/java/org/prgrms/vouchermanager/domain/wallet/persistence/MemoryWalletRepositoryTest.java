package org.prgrms.vouchermanager.domain.wallet.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.wallet.domain.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryWalletRepositoryTest {

    private final MemoryWalletRepository memoryWalletRepository = new MemoryWalletRepository();

    @Test
    @DisplayName("Wallet을 저장 할 수 있다.")
    void insert() {
        //given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId, voucherId);

        //when
        memoryWalletRepository.insert(wallet);

        //then
        assertThat(memoryWalletRepository.findByVoucherId(voucherId).get()).isEqualTo(wallet);
        assertThat(memoryWalletRepository.findByCustomerId(customerId)).contains(wallet);
    }

    @Test
    @DisplayName("voucherId로 wallet을 찾을 수 있다.")
    void findByVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId, voucherId);
        memoryWalletRepository.insert(wallet);

        //when
        Optional<Wallet> findWallet = memoryWalletRepository.findByVoucherId(voucherId);

        //then
        assertThat(findWallet.get()).isEqualTo(wallet);
    }

    @Test
    @DisplayName("customerId로 wallet List를 찾을 수 있다.")
    void findByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        List<Wallet> walletList = new ArrayList<>();

        IntStream.range(1, 10).forEach(i -> {
            Wallet wallet = new Wallet(UUID.randomUUID(), customerId, UUID.randomUUID());
            walletList.add(wallet);
            memoryWalletRepository.insert(wallet);
        });

        //when
        List<Wallet> findWallets = memoryWalletRepository.findByCustomerId(customerId);

        //then
        assertThat(findWallets).containsAll(walletList);
    }

    @Test
    @DisplayName("wallet을 삭제할 수 있다.")
    void delete() {
        //given
        UUID customerId = UUID.randomUUID();
        Wallet wallet = new Wallet(UUID.randomUUID(), customerId, UUID.randomUUID());
        memoryWalletRepository.insert(wallet);

        //when
        memoryWalletRepository.delete(wallet);

        //then
        assertThat(memoryWalletRepository.findByVoucherId(customerId)).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("voucherId로 wallet을 삭제할 수 있다.")
    void deleteByVoucherId() {
        //given
        UUID voucherId = UUID.randomUUID();
        Wallet wallet = new Wallet(UUID.randomUUID(), UUID.randomUUID(), voucherId);
        memoryWalletRepository.insert(wallet);

        //when
        memoryWalletRepository.deleteByVoucherId(voucherId);

        //then
        assertThat(memoryWalletRepository.findByVoucherId(voucherId)).isEqualTo(Optional.empty());
    }
}