package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("지갑을 저장할 수 있다.")
    void save() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test@email.com", false);
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Wallet newWallet = new Wallet(customer, voucher);

        // when
        walletRepository.save(newWallet);

        // then
        Wallet savedWallet = walletRepository.findAll(new GetWalletsRequestDto()).get(0);
        assertThat(savedWallet.getCustomer().getId()).isEqualTo(newWallet.getCustomer().getId());
        assertThat(savedWallet.getVoucher().getId()).isEqualTo(newWallet.getVoucher().getId());
        assertThat(savedWallet.isUsed()).isEqualTo(newWallet.isUsed());
    }

    @Test
    @DisplayName("지갑 목록을 저장할 수 있다.")
    void saveAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1@email.com", false);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2@email.com", true);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        Wallet newWallet1 = new Wallet(customer1, voucher1);
        Wallet newWallet2 = new Wallet(customer2, voucher2);

        // when
        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        // then
        List<Wallet> savedWallets = walletRepository.findAll(new GetWalletsRequestDto());

        assertThat(savedWallets).hasSize(2);
        assertThat(savedWallets).extracting(Wallet::getCustomer)
                .containsExactlyInAnyOrder(newWallet1.getCustomer(), newWallet2.getCustomer());
        assertThat(savedWallets).extracting(Wallet::getVoucher)
                .containsExactlyInAnyOrder(newWallet1.getVoucher(), newWallet2.getVoucher());
        assertThat(savedWallets).extracting(Wallet::isUsed)
                .containsExactlyInAnyOrder(newWallet1.isUsed(), newWallet2.isUsed());
    }

    @Test
    @DisplayName("지갑을 아이디로 조회할 수 있다.")
    void findById() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test@email.com", false);
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Wallet newWallet = new Wallet(customer, voucher);
        walletRepository.save(newWallet);

        // when
        Optional<Wallet> foundWallet = walletRepository.findById(newWallet.getId());

        // then
        assertThat(foundWallet).isPresent();
        assertThat(foundWallet.get().getCustomer().getId()).isEqualTo(newWallet.getCustomer().getId());
        assertThat(foundWallet.get().getVoucher().getId()).isEqualTo(newWallet.getVoucher().getId());
        assertThat(foundWallet.get().isUsed()).isEqualTo(newWallet.isUsed());
    }

    @Test
    @DisplayName("모든 지갑을 조회할 수 있다.")
    void findAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1@email.com", false);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2@email.com", true);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        Wallet newWallet1 = new Wallet(customer1, voucher1);
        Wallet newWallet2 = new Wallet(customer2, voucher2);

        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        // when
        List<Wallet> foundWallets = walletRepository.findAll(new GetWalletsRequestDto());

        // then
        assertThat(foundWallets).hasSize(2);
        assertThat(foundWallets).extracting(Wallet::getCustomer)
                .containsExactlyInAnyOrder(newWallet1.getCustomer(), newWallet2.getCustomer());
        assertThat(foundWallets).extracting(Wallet::getVoucher)
                .containsExactlyInAnyOrder(newWallet1.getVoucher(), newWallet2.getVoucher());
        assertThat(foundWallets).extracting(Wallet::isUsed)
                .containsExactlyInAnyOrder(newWallet1.isUsed(), newWallet2.isUsed());
    }

    @Test
    @DisplayName("특정 고객의 모든 지갑을 조회할 수 있다.")
    void findAllByCustomer() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1@email.com", false);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2@email.com", true);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        Wallet newWallet1 = new Wallet(customer1, voucher1);
        Wallet newWallet2 = new Wallet(customer2, voucher2);

        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        GetWalletsRequestDto request = new GetWalletsRequestDto();
        request.setCustomerId(customer1.getId());

        // when
        List<Wallet> foundWallets = walletRepository.findAll(request);

        // then
        assertThat(foundWallets).hasSize(1);
        assertThat(foundWallets).extracting(Wallet::getCustomer)
                .containsExactlyInAnyOrder(newWallet1.getCustomer());
    }

    @Test
    @DisplayName("특정 바우처의 모든 지갑을 조회할 수 있다.")
    void findAllByVoucher() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1@email.com", false);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2@email.com", true);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        Wallet newWallet1 = new Wallet(customer1, voucher1);
        Wallet newWallet2 = new Wallet(customer2, voucher2);

        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        GetWalletsRequestDto request = new GetWalletsRequestDto();
        request.setVoucherId(voucher1.getId());

        // when
        List<Wallet> foundWallets = walletRepository.findAll(request);

        // then
        assertThat(foundWallets).hasSize(1);
        assertThat(foundWallets).extracting(Wallet::getVoucher)
                .containsExactlyInAnyOrder(newWallet1.getVoucher());
    }

    @Test
    @DisplayName("모든 지갑을 삭제할 수 있다.")
    void deleteAll() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1@email.com", false);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2@email.com", true);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        Wallet newWallet1 = new Wallet(customer1, voucher1);
        Wallet newWallet2 = new Wallet(customer2, voucher2);

        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        // when
        walletRepository.deleteAll();

        // then
        List<Wallet> foundWallets = walletRepository.findAll(new GetWalletsRequestDto());
        assertThat(foundWallets).isEmpty();
    }
}
