package com.programmers.wallet.service;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WalletServiceImplTest {

    @Autowired
    WalletServiceImpl walletService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherRepository voucherRepository;


    @Test
    @Transactional
    @DisplayName("등록 회원의 지갑에 바우처를 저장할 수 있다.")
    void 지갑_바우처_저장_성공() {
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "kiseo12@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);

        Customer result = walletService.assignVoucher(customerUUID, voucherUUID);

        assertThat(result.getWallet(), hasSize(1));
    }

    @Test
    @Transactional
    @DisplayName("미등록 회원의 지갑에는 바우처를 저장할 수 없다.")
    void 지갑_바우처_저장_실패() {
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        voucherRepository.registerVoucher(voucher);

        assertThrows(RuntimeException.class, () -> walletService.assignVoucher(customerUUID, voucherUUID));
    }

    @Test
    @Transactional
    @DisplayName("미등록 바우처는 지갑에 저장할 수 없다.")
    void 지갑_바우처_저장_실패2() {
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);

        assertThrows(RuntimeException.class, () -> walletService.assignVoucher(customerUUID, voucherUUID));
    }

    @Test
    @Transactional
    @DisplayName("특정 고객의 바우처를 조회할 수 있다.")
    void 지갑_바우처_조회() {
        //given
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customerUUID, voucherUUID);

        //when
        List<Voucher> vouchers = walletService.searchVouchersByCustomerId(customerUUID);

        //then
        assertThat(vouchers, hasSize(1));
    }

    @Test
    @Transactional
    @DisplayName("특정 바우처를 지닌 고객을 조회할 수 있다.")
    void 지갑_고객_조회() {
        //given
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customerUUID, voucherUUID);

        //when
        Customer findOne = walletService.searchCustomerByVoucherId(voucherUUID);

        //then
        assertEquals(customer, findOne);
    }

    @Test
    @Transactional
    @DisplayName("특정 고객의 바우처를 삭제할 수 있다.")
    void 지갑_바우처_삭제() {
        //given
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);

        walletService.assignVoucher(customerUUID, voucherUUID);
        List<Voucher> beforeWallet = walletService.searchVouchersByCustomerId(customerUUID);

        assertThat(beforeWallet, hasSize(1));

        //when
        walletService.removeCustomerVoucher(customerUUID, voucherUUID);

        //then
        List<Voucher> afterWallet = walletService.searchVouchersByCustomerId(customerUUID);
        assertThat(afterWallet.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @DisplayName("회원의 지갑에 없는 바우처는 삭제할 수 없다.")
    void 지갑_바우처_삭제_실패1() {
        //given
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();
        UUID strangeUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customerUUID, voucherUUID);

        //when, then
        assertThrows(RuntimeException.class, () -> walletService.removeCustomerVoucher(customerUUID, strangeUUID));
    }

    @Test
    @Transactional
    @DisplayName("미등록 회원의 바우처는 삭제할 수 없다.")
    void 지갑_바우처_삭제_실패2() {
        //given
        UUID customerUUID = UUID.randomUUID();
        UUID voucherUUID = UUID.randomUUID();

        Customer customer = new Customer(customerUUID, "kiseo", "hello@gmail.com", LocalDateTime.now());
        Customer stranger = new Customer(UUID.randomUUID(), "stranger", "bad@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(voucherUUID, FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customerUUID, voucherUUID);

        //when, then
        assertThrows(RuntimeException.class, () -> walletService.removeCustomerVoucher(stranger.getCustomerId(), voucherUUID));

    }
}