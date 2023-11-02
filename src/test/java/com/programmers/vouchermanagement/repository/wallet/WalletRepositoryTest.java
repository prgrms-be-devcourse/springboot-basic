package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.customer.request.GetCustomersRequestDto;
import com.programmers.vouchermanagement.dto.voucher.request.GetVouchersRequestDto;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import com.programmers.vouchermanagement.repository.ContainerBaseTest;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class WalletRepositoryTest extends ContainerBaseTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("지갑을 저장할 수 있다.")
    void save() {
        // given
        Wallet newWallet = getNewWallet();

        // when
        walletRepository.save(newWallet);

        // then
        Wallet savedWallet = walletRepository.findAll(new GetWalletsRequestDto()).get(0);
        assertThat(savedWallet.getCustomer()).isEqualTo(newWallet.getCustomer());
        assertThat(savedWallet.getVoucher()).isEqualTo(newWallet.getVoucher());
        assertThat(savedWallet.isUsed()).isEqualTo(newWallet.isUsed());
    }

    @Test
    @DisplayName("지갑 목록을 저장할 수 있다.")
    void saveAll() {
        // given
        List<Wallet> newWallets = getTwoNewWallets();
        Wallet newWallet1 = newWallets.get(0);
        Wallet newWallet2 = newWallets.get(1);

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
        Wallet newWallet = getNewWallet();
        walletRepository.save(newWallet);

        Wallet savedWallet = walletRepository.findAll(new GetWalletsRequestDto()).get(0);

        // when
        Optional<Wallet> foundWallet = walletRepository.findById(savedWallet.getId());

        // then
        assertThat(foundWallet).isPresent();
        assertThat(foundWallet.get().getCustomer()).isEqualTo(newWallet.getCustomer());
        assertThat(foundWallet.get().getVoucher()).isEqualTo(newWallet.getVoucher());
        assertThat(foundWallet.get().isUsed()).isEqualTo(newWallet.isUsed());
    }

    @Test
    @DisplayName("모든 지갑을 조회할 수 있다.")
    void findAll() {
        // given
        List<Wallet> newWallets = getTwoNewWallets();
        Wallet newWallet1 = newWallets.get(0);
        Wallet newWallet2 = newWallets.get(1);
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
        Customer newCustomer1 = new Customer("test1@email.com", false);
        Customer newCustomer2 = new Customer("test2@email.com", true);
        customerRepository.saveAll(List.of(newCustomer1, newCustomer2));
        List<Customer> savedCustomers = customerRepository.findAll(new GetCustomersRequestDto());

        Voucher newVoucher1 = new FixedAmountVoucher(1000L);
        Voucher newVoucher2 = new PercentDiscountVoucher(10L);
        voucherRepository.saveAll(List.of(newVoucher1, newVoucher2));
        List<Voucher> savedVouchers = voucherRepository.findAll(new GetVouchersRequestDto());

        Wallet newWallet1 = new Wallet(savedCustomers.get(0), savedVouchers.get(0));
        Wallet newWallet2 = new Wallet(savedCustomers.get(1), savedVouchers.get(1));
        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        GetWalletsRequestDto request = GetWalletsRequestDto.builder()
                .customerId(savedCustomers.get(0).getId())
                .build();

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
        Customer newCustomer1 = new Customer("test1@email.com", false);
        Customer newCustomer2 = new Customer("test2@email.com", true);
        customerRepository.saveAll(List.of(newCustomer1, newCustomer2));
        List<Customer> savedCustomers = customerRepository.findAll(new GetCustomersRequestDto());

        Voucher newVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher newVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        voucherRepository.saveAll(List.of(newVoucher1, newVoucher2));
        List<Voucher> savedVouchers = voucherRepository.findAll(new GetVouchersRequestDto());

        Wallet newWallet1 = new Wallet(savedCustomers.get(0), savedVouchers.get(0));
        Wallet newWallet2 = new Wallet(savedCustomers.get(1), savedVouchers.get(1));
        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        GetWalletsRequestDto request = GetWalletsRequestDto.builder()
                .voucherId(savedVouchers.get(0).getId())
                .build();

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
        List<Wallet> newWallets = getTwoNewWallets();
        Wallet newWallet1 = newWallets.get(0);
        Wallet newWallet2 = newWallets.get(1);
        walletRepository.saveAll(List.of(newWallet1, newWallet2));

        // when
        walletRepository.deleteAll();

        // then
        List<Wallet> foundWallets = walletRepository.findAll(new GetWalletsRequestDto());
        assertThat(foundWallets).isEmpty();
    }

    private Wallet getNewWallet() {
        Customer newCustomer = new Customer("test@email.com", false);
        customerRepository.save(newCustomer);
        Customer savedCustomer = customerRepository.findAll(new GetCustomersRequestDto()).get(0);

        Voucher newVoucher = new FixedAmountVoucher(1000L);
        voucherRepository.save(newVoucher);
        Voucher savedVoucher = voucherRepository.findAll(new GetVouchersRequestDto()).get(0);

        return new Wallet(savedCustomer, savedVoucher);
    }

    private List<Wallet> getTwoNewWallets() {
        Customer newCustomer1 = new Customer("test1@email.com", false);
        Customer newCustomer2 = new Customer("test2@email.com", true);
        customerRepository.saveAll(List.of(newCustomer1, newCustomer2));
        List<Customer> savedCustomers = customerRepository.findAll(new GetCustomersRequestDto());

        Voucher newVoucher1 = new FixedAmountVoucher(1000L);
        Voucher newVoucher2 = new PercentDiscountVoucher(10L);
        voucherRepository.saveAll(List.of(newVoucher1, newVoucher2));
        List<Voucher> savedVouchers = voucherRepository.findAll(new GetVouchersRequestDto());

        Wallet newWallet1 = new Wallet(savedCustomers.get(0), savedVouchers.get(0));
        Wallet newWallet2 = new Wallet(savedCustomers.get(1), savedVouchers.get(1));

        return List.of(newWallet1, newWallet2);
    }
}
