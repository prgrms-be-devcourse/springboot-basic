package org.promgrammers.springbootbasic.domain.wallet.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.controller.CommandLineController;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.promgrammers.springbootbasic.domain.wallet.model.Wallet;
import org.promgrammers.springbootbasic.exception.repository.NonExistentDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yaml")
@ActiveProfiles("jdbc")
class JdbcWalletRepositoryTest {

    @MockBean
    private CommandLineController controller;

    @Autowired
    JdbcCustomerRepository customerRepository;
    @Autowired
    JdbcVoucherRepository voucherRepository;
    @Autowired
    JdbcWalletRepository walletRepository;

    private Wallet wallet;

    private Voucher voucher;

    private Customer customer;

    @BeforeEach
    void init() {
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
        walletRepository.deleteAll();
        voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        customer = new Customer(UUID.randomUUID(), "HONG");
        voucherRepository.insert(voucher);
        customerRepository.save(customer);
    }


    @Test
    @DisplayName("저장 성공 - 지갑 저장")
    void saveWalletSuccessTest() throws Exception {

        //given
        wallet = new Wallet(voucher, customer);

        //when
        Wallet savedWallet = walletRepository.save(wallet);

        //then
        assertNotNull(savedWallet);
        assertThat(savedWallet.getWalletId()).isEqualTo(wallet.getWalletId());
        assertThat(savedWallet.getCustomer()).isEqualTo(wallet.getCustomer());
        assertThat(savedWallet.getVoucher()).isEqualTo(wallet.getVoucher());
    }

    @Test
    @DisplayName("전체 지갑 조회")
    void findAllWalletsTest() {

        //given
        List<Wallet> wallets = Arrays.asList(
                new Wallet(voucher, customer),
                new Wallet(voucher, customer),
                new Wallet(voucher, customer)
        );
        wallets.forEach(walletRepository::save);

        //when
        List<Wallet> foundWallets = walletRepository.findAll();

        //then
        assertThat(foundWallets).hasSize(wallets.size());
        assertThat(foundWallets).containsExactlyElementsOf(wallets);
    }

    @Test
    @DisplayName("지갑 ID로 조회 성공 - 존재하는 경우")
    void findAllWalletByIdSuccessTest() throws Exception {

        //given
        wallet = new Wallet(voucher, customer);
        walletRepository.save(wallet);

        //when
        Optional<Wallet> foundWallet = walletRepository.findById(wallet.getWalletId());

        //then
        assertThat(foundWallet).isPresent();
        assertThat(foundWallet.get()).isEqualTo(wallet);
    }

    @Test
    @DisplayName("지갑 ID로 조회 - 존재하지 않는 경우")
    void findWalletByIdNonExistingTest() {

        //given
        UUID nonExistingId = UUID.randomUUID();

        //when
        Optional<Wallet> foundWallet = walletRepository.findById(nonExistingId);

        //then
        assertThat(foundWallet).isEmpty();
    }

    @Test
    @DisplayName("고객 ID로 지갑 목록 조회")
    void findAllWalletsByCustomerIdTest() {

        //given
        Customer anotherCustomer = new Customer(UUID.randomUUID(), "KIM");
        customerRepository.save(anotherCustomer);

        Wallet wallet1 = new Wallet(voucher, customer);
        Wallet wallet2 = new Wallet(voucher, customer);
        Wallet wallet3 = new Wallet(voucher, anotherCustomer);
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        //when
        List<Wallet> foundWallets = walletRepository.findAllWalletByCustomerId(customer.getCustomerId());

        //then
        assertThat(foundWallets).hasSize(2);
        assertThat(foundWallets).containsExactly(wallet1, wallet2);
    }

    @Test
    @DisplayName("바우처 ID로 지갑 목록 조회")
    void findAllWalletsByVoucherIdTest() {

        //given
        FixedAmountVoucher anotherVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        voucherRepository.insert(anotherVoucher);

        Wallet wallet1 = new Wallet(voucher, customer);
        Wallet wallet2 = new Wallet(voucher, customer);
        Wallet wallet3 = new Wallet(anotherVoucher, customer);
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        //when
        List<Wallet> foundWallets = walletRepository.findAllWalletByVoucherId(voucher.getVoucherId());

        //then
        assertThat(foundWallets).hasSize(2);
        assertThat(foundWallets).containsExactly(wallet1, wallet2);
    }

    @Test
    @DisplayName("전체 지갑 삭제")
    void deleteAllWalletsTest() {

        //given
        Wallet wallet1 = new Wallet(voucher, customer);
        Wallet wallet2 = new Wallet(voucher, customer);
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);

        //when
        walletRepository.deleteAll();

        //then
        List<Wallet> foundWallets = walletRepository.findAll();
        assertThat(foundWallets).isEmpty();
    }

    @Test
    @DisplayName("지갑 ID로 지갑 삭제 - 존재하는 경우")
    void deleteWalletByIdSuccessTest() {

        //given
        Wallet wallet = new Wallet(voucher, customer);
        walletRepository.save(wallet);

        //when
        walletRepository.deleteById(wallet.getWalletId());

        //then
        List<Wallet> foundWallets = walletRepository.findAll();
        assertThat(foundWallets).isEmpty();
    }

    @Test
    @DisplayName("지갑 ID로 지갑 삭제 - 존재하지 않는 경우")
    void deleteWalletByIdNonExistingTest() {
        //given
        UUID nonExistingId = UUID.randomUUID();

        //when -> then
        assertThrows(NonExistentDomainException.class, () -> walletRepository.deleteById(nonExistingId));
    }

}