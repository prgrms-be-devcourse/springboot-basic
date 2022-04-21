package org.prgms.wallet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.prgms.EmbeddedTestDbInitializer;
import org.prgms.TestDbConfig;
import org.prgms.customer.Customer;
import org.prgms.customer.repository.CustomerRepository;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.PercentDiscountVoucher;
import org.prgms.voucher.Voucher;
import org.prgms.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;


@SpringJUnitConfig(value = {TestDbConfig.class, WalletRepositoryTest.Config.class}, initializers = EmbeddedTestDbInitializer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "org.prgms.wallet")
    static class Config {
    }

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

    @BeforeAll
    void setup() {
        customerRepository.save(newCustomer);
        customerRepository.save(newCustomer2);

        voucherRepository.save(voucher);
        voucherRepository.save(voucher2);
    }

    @BeforeEach
    void beforeEach() {
        walletRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 지갑을 조회 & 지갑 저장 테스트")
    void findAllTest() {
        walletRepository.save(wallet1);
        walletRepository.save(wallet3);
        Assertions.assertThat(walletRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("특정 고객에 대한 바우처 조회")
    void findByCustomerId() {
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        Assertions.assertThat(walletRepository.findByCustomerId(newCustomer.customerId())).hasSize(2);
        Assertions.assertThat(walletRepository.findByCustomerId(newCustomer2.customerId())).hasSize(1);
    }

    @Test
    @DisplayName("특정 바우처에 대한 고객 조회")
    void findByVoucherId() {
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
        walletRepository.save(wallet3);

        Assertions.assertThat(walletRepository.findByVoucherId(voucher.getVoucherId())).hasSize(2);
        Assertions.assertThat(walletRepository.findByVoucherId(voucher2.getVoucherId())).hasSize(1);
    }
}