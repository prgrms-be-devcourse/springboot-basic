package org.prgrms.prgrmsspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.FixedAmountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.PercentDiscountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.repository.wallet.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class WalletServiceTest {

    @Autowired
    private WalletService walletService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private WalletRepository repository;

    @AfterEach
    void tearDown() {
        repository.clear();
    }


    @DisplayName("고객에게 바우처를 할당할 수 있다.")
    @Test
    void allocateVoucherToCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);

        customerService.create(customer);
        voucherService.create(voucher);

        //when
        Wallet wallet = walletService.allocateVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId());

        //then
        assertThat(wallet.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(wallet.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @DisplayName("유효한 고객 아이디가 아닌 경우(고객이 존재하지 않는 경우 등) 바우처를 할당할 수 없다.")
    @Test
    void allocateFailToInvalidCustomerId() {
        //given
        UUID invalidCustomerId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);

        voucherService.create(voucher);

        //when //then
        assertThatThrownBy(() -> walletService.allocateVoucherToCustomer(invalidCustomerId, voucher.getVoucherId()))
                .isInstanceOf(DataAccessException.class)
                .hasMessage(ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
    }

    @DisplayName("유효한 바우처 아이디가 아닌 경우(바우처가 존재하지 않는 경우 등) 고객에게 바우처를 할당할 수 없다.")
    @Test
    void allocateFailFromInvalidVoucherId() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        UUID invalidVoucherId = UUID.randomUUID();

        customerService.create(customer);

        //when //then
        assertThatThrownBy(() -> walletService.allocateVoucherToCustomer(customer.getCustomerId(), invalidVoucherId))
                .isInstanceOf(DataAccessException.class)
                .hasMessage(ExceptionMessage.INSERT_QUERY_FAILED.getMessage());
    }

    @DisplayName("고객에게 할당된 바우처들을 조회할 수 있다.")
    @Test
    void findAllocatedVoucherByCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testCustomer", "test@mail.com");
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 200000);
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
        customerService.create(customer);
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        vouchers.forEach(voucherService::create);
        vouchers.forEach(voucher -> walletService.allocateVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId()));

        //when
        List<Voucher> voucherListByCustomerId = walletService.findVoucherListByCustomerId(customer.getCustomerId());

        //then
        assertThat(voucherListByCustomerId).hasSize(vouchers.size())
                .containsOnlyOnceElementsOf(vouchers);
    }

    @DisplayName("고객에게 할당된 바우처가 없는 경우, 빈 리스트를 반환한다.")
    @Test
    void findEmptyAllocatedVouchersByCustomer() {
        //given
        UUID targetCustomerId = UUID.randomUUID();

        //when
        List<Voucher> voucherListByCustomerId = walletService.findVoucherListByCustomerId(targetCustomerId);

        //then
        assertThat(voucherListByCustomerId).isEmpty();
    }

    @DisplayName("고객에게 할당된 바우처들을 지울 수 있다.")
    @Test
    void deleteVoucherByCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customerName", "customer@mail.com");
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 200000);
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
        customerService.create(customer);
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        vouchers.forEach(voucherService::create);

        vouchers.forEach(voucher -> walletService.allocateVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId()));

        //when
        walletService.deleteVouchersByCustomerId(customer.getCustomerId());

        //then
        assertThat(walletService.findVoucherListByCustomerId(customer.getCustomerId()))
                .isEmpty();
    }

    @DisplayName("해당 바우처를 보유한 고객을 조회할 수 있다.")
    @Test
    void findCustomerByVoucher() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "testName", "test@mail.com");
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 30000);

        customerService.create(customer);
        voucherService.create(voucher);

        walletService.allocateVoucherToCustomer(customer.getCustomerId(), voucher.getVoucherId());

        //when
        Customer findCustomer = walletService.findCustomerByVoucherId(voucher.getVoucherId());

        //then
        assertThat(findCustomer).isEqualTo(customer);
    }
}

