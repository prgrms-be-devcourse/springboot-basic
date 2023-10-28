package org.prgrms.prgrmsspring.repository.wallet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.FixedAmountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.PercentDiscountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.repository.user.CustomerRepository;
import org.prgrms.prgrmsspring.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class DBWalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @AfterEach
    void tearDown() {
        walletRepository.clear();
        customerRepository.clear();
        voucherRepository.clear();
    }

    @DisplayName("고객에게 바우처를 할당할 수 있다.")
    @Test
    void createWallet() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        customerRepository.insert(customer);
        voucherRepository.insert(voucher);

        //when
        Wallet wallet = walletRepository.allocateVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId());

        //then
        assertThat(wallet.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(wallet.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @DisplayName("고객이 가지고 있는 바우처들의 아이디를 조회할 수 있다.")
    @Test
    void findVoucherIdListByCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        UUID customerId = customer.getCustomerId();
        customerRepository.insert(customer);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 35);
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        vouchers.forEach(voucherRepository::insert);
        vouchers.forEach(voucher -> walletRepository.allocateVoucherToCustomer(customerId, voucher.getVoucherId()));

        List<UUID> voucherIdList = vouchers.stream().map(Voucher::getVoucherId).toList();

        //when
        List<UUID> voucherIdListByCustomerId = walletRepository.findVoucherIdListByCustomerId(customerId);

        //then
        assertThat(voucherIdListByCustomerId).containsOnlyOnceElementsOf(voucherIdList);
    }

    @DisplayName("고객이 가지고 있는 바우처들을 삭제할 수 있다.")
    @Test
    void deleteVoucherByCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        UUID customerId = customer.getCustomerId();
        customerRepository.insert(customer);

        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 35);
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        vouchers.forEach(voucherRepository::insert);
        vouchers.forEach(voucher -> walletRepository.allocateVoucherToCustomer(customerId, voucher.getVoucherId()));

        List<UUID> voucherIdList = vouchers.stream().map(Voucher::getVoucherId).toList();

        //when
        walletRepository.deleteVouchersByCustomerId(customerId);

        //then
        List<UUID> voucherIdListByCustomerId = walletRepository.findVoucherIdListByCustomerId(customerId);
        assertThat(voucherIdListByCustomerId).isNullOrEmpty();
    }

    @DisplayName("특정 바우처를 가지고 있는 고객을 조회할 수 있다.")
    @Test
    void findCustomerByVoucher() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        UUID customerId = customer.getCustomerId();
        customerRepository.insert(customer);

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10000);
        UUID voucherId = voucher.getVoucherId();
        voucherRepository.insert(voucher);

        walletRepository.allocateVoucherToCustomer(customerId, voucherId);
        //when

        Optional<Customer> customerByVoucherId = walletRepository.findCustomerByVoucherId(voucherId);
        //then
        assertThat(customerByVoucherId).contains(customer);
    }


}