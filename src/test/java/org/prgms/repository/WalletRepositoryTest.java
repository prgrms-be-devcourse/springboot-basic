package org.prgms.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class WalletRepositoryTest extends RepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private final Customer newCustomer = new Customer(UUID.randomUUID(), "user-test", "user-test@gmail.com");
    private final Customer newCustomer2 = new Customer(UUID.randomUUID(), "user-test2", "user-test2@gmail.com");
    private final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
    private final Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

    private final Wallet wallet1 = new Wallet(UUID.randomUUID(), newCustomer.customerId(), voucher.getVoucherId());
    private final Wallet wallet2 = new Wallet(UUID.randomUUID(), newCustomer2.customerId(), voucher.getVoucherId());
    private final Wallet wallet3 = new Wallet(UUID.randomUUID(), newCustomer.customerId(), voucher2.getVoucherId());

    @BeforeEach
    void setup() {
        // Foreign Key 조건의 만족을 위해 미리 insert 해줌
        customerRepository.save(newCustomer);
        customerRepository.save(newCustomer2);

        voucherRepository.save(voucher);
        voucherRepository.save(voucher2);
    }

    @Test
    @DisplayName("모든 지갑을 조회 & 지갑 저장 테스트")
    void findAllTest() {
        walletRepository.save(wallet1);
        walletRepository.save(wallet3);

        assertThat(walletRepository.findAll()).containsExactlyInAnyOrder(wallet1, wallet3);
    }

    @Test
    @DisplayName("특정 고객에 대한 바우처 조회")
    void findByCustomerId() {
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        assertThat(walletRepository.findByCustomerId(newCustomer.customerId())).containsExactlyInAnyOrder(wallet1, wallet3);
        assertThat(walletRepository.findByCustomerId(newCustomer2.customerId())).containsExactly(wallet2);
    }

    @Test
    @DisplayName("특정 바우처에 대한 고객 조회")
    void findByVoucherId() {
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        assertThat(walletRepository.findByVoucherId(voucher.getVoucherId())).containsExactlyInAnyOrder(wallet1, wallet2);
        assertThat(walletRepository.findByVoucherId(voucher2.getVoucherId())).containsExactly(wallet3);
    }
}