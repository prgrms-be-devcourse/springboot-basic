package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.repository.CustomerRepository;
import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
class VoucherWalletRepositoryTest {

    @Autowired
    private VoucherWalletRepository voucherWalletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    private UUID customerUUID1;
    private UUID voucherUUID1;
    private UUID voucherUUID2;

    @BeforeEach
    void setUp() {
        customerUUID1 = UUID.randomUUID();
        voucherUUID1 = UUID.randomUUID();
        voucherUUID2 = UUID.randomUUID();

        customerRepository.save(new Customer(customerUUID1, "Customer1", "customer1@test.com", CustomerStatus.WHITE));
        voucherRepository.save(new FixedAmountVoucher(voucherUUID1, 10));
        voucherRepository.save(new PercentDiscountVoucher(voucherUUID2, 20));
    }

    @AfterEach
    void tearDown() {
        voucherWalletRepository.deleteByCustomerId(customerUUID1);
        customerRepository.deleteById(customerUUID1);
        voucherRepository.deleteById(voucherUUID1);
        voucherRepository.deleteById(voucherUUID2);
    }

    @Test
    void addVoucherToWallet() {
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);

        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);

        assertThat(vouchers, hasItem(voucherUUID1));
    }


    @Test
    void removeVoucherFromWallet() {
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.removeVoucherFromWallet(customerUUID1, voucherUUID1);

        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);

        assertThat(vouchers, not(hasItem(voucherUUID1)));
    }

    @Test
    void findVoucherIdsByCustomerId() {
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID2);

        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);

        assertThat(vouchers, hasItems(voucherUUID1, voucherUUID2));
    }

    @Test
    void findCustomerIdsByVoucherId() {
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);

        Set<UUID> customers = voucherWalletRepository.findCustomerIdsByVoucherId(voucherUUID1);

        assertThat(customers, hasItem(customerUUID1));
    }

    @Test
    void deleteByCustomerId() {
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID2);

        voucherWalletRepository.deleteByCustomerId(customerUUID1);

        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);

        assertThat(vouchers, empty());
    }
}