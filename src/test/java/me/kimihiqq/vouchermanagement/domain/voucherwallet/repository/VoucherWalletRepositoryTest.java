package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.repository.CustomerRepository;
import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("지갑에 바우처을 추가할 수 있다")
    void addVoucherToWallet() {
        // when
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);

        // then
        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);
        assertThat(vouchers, hasItem(voucherUUID1));
    }

    @Test
    @DisplayName("지갑에서 바우처을 제거할 수 있다")
    void removeVoucherFromWallet() {
        // given
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);
        // when
        voucherWalletRepository.removeVoucherFromWallet(customerUUID1, voucherUUID1);

        // then
        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);
        assertThat(vouchers, not(hasItem(voucherUUID1)));
    }

    @Test
    @DisplayName("고객 ID로 바우처 ID를 찾을 수 있다")
    void findVoucherIdsByCustomerId() {
        // given
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID2);

        // when
        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);

        // then
        assertThat(vouchers, hasItems(voucherUUID1, voucherUUID2));
    }

    @Test
    @DisplayName("바우처 ID로 고객 ID를 찾을 수 있다")
    void findCustomerIdsByVoucherId() {
        // given
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);

        // when
        Set<UUID> customers = voucherWalletRepository.findCustomerIdsByVoucherId(voucherUUID1);

        // then
        assertThat(customers, hasItem(customerUUID1));
    }

    @Test
    @DisplayName("고객 ID를 기반으로 지갑의 내용을 삭제할 수 있다")
    void deleteByCustomerId() {
        // given
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.addVoucherToWallet(customerUUID1, voucherUUID2);

        // when
        voucherWalletRepository.deleteByCustomerId(customerUUID1);

        // then
        Set<UUID> vouchers = voucherWalletRepository.findVoucherIdsByCustomerId(customerUUID1);
        assertThat(vouchers, empty());
    }
}