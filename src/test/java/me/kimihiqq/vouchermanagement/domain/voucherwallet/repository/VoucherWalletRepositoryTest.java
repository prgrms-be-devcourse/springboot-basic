package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.repository.CustomerRepository;
import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.repository.VoucherRepository;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.VoucherWallet;
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
    @DisplayName("지갑에 바우처를 추가할 수 있다")
    void addVoucherToWallet() {
        // when
        voucherWalletRepository.addVoucherToWallet(new VoucherWallet(customerUUID1, voucherUUID1));

        // then
        Set<VoucherWallet> vouchers = voucherWalletRepository.findVoucherWalletsByCustomerId(customerUUID1);
        assertThat(vouchers, hasItem(new VoucherWallet(customerUUID1, voucherUUID1)));
    }

    @Test
    @DisplayName("지갑에서 바우처를 제거할 수 있다")
    void removeVoucherFromWallet() {
        // given
        VoucherWallet voucherWallet = new VoucherWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.addVoucherToWallet(voucherWallet);
        // when
        voucherWalletRepository.removeVoucherFromWallet(voucherWallet);

        // then
        Set<VoucherWallet> vouchers = voucherWalletRepository.findVoucherWalletsByCustomerId(customerUUID1);
        assertThat(vouchers, not(hasItem(new VoucherWallet(customerUUID1, voucherUUID1))));
    }

    @Test
    @DisplayName("고객 ID로 바우처 ID를 찾을 수 있다")
    void findVoucherWalletsByCustomerId() {
        // given
        VoucherWallet voucherWallet1 = new VoucherWallet(customerUUID1, voucherUUID1);
        VoucherWallet voucherWallet2 = new VoucherWallet(customerUUID1, voucherUUID2);
        voucherWalletRepository.addVoucherToWallet(voucherWallet1);
        voucherWalletRepository.addVoucherToWallet(voucherWallet2);

        // when
        Set<VoucherWallet> vouchers = voucherWalletRepository.findVoucherWalletsByCustomerId(customerUUID1);

        // then
        assertThat(vouchers, hasItems(voucherWallet1, voucherWallet2));
    }

    @Test
    @DisplayName("바우처 ID로 고객 ID를 찾을 수 있다")
    void findVoucherWalletsByVoucherId() {
        // given
        VoucherWallet voucherWallet = new VoucherWallet(customerUUID1, voucherUUID1);
        voucherWalletRepository.addVoucherToWallet(voucherWallet);

        // when
        Set<VoucherWallet> customers = voucherWalletRepository.findVoucherWalletsByVoucherId(voucherUUID1);

        // then
        assertThat(customers, hasItem(voucherWallet));
    }

    @Test
    @DisplayName("고객 ID를 기반으로 지갑의 내용을 삭제할 수 있다")
    void deleteByCustomerId() {
        // given
        VoucherWallet voucherWallet1 = new VoucherWallet(customerUUID1, voucherUUID1);
        VoucherWallet voucherWallet2 = new VoucherWallet(customerUUID1, voucherUUID2);
        voucherWalletRepository.addVoucherToWallet(voucherWallet1);
        voucherWalletRepository.addVoucherToWallet(voucherWallet2);

        // when
        voucherWalletRepository.deleteByCustomerId(customerUUID1);

        // then
        Set<VoucherWallet> vouchers = voucherWalletRepository.findVoucherWalletsByCustomerId(customerUUID1);
        assertThat(vouchers, empty());
    }
}
