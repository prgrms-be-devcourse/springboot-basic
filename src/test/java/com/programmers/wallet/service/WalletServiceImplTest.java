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

import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "kiseo12@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);

        assertDoesNotThrow(() -> walletService.assignVoucher(customer, voucher));
        assertThat(customer.getWallet(), hasSize(1));
    }

    @Test
    @Transactional
    @DisplayName("미등록 회원의 지갑에는 바우처를 저장할 수 없다.")
    void 지갑_바우처_저장_실패() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 50000);

        voucherRepository.registerVoucher(voucher);

        assertThrows(RuntimeException.class, () -> walletService.assignVoucher(customer, voucher));
    }

    @Test
    @Transactional
    @DisplayName("미등록 바우처는 지갑에 저장할 수 없다.")
    void 지갑_바우처_저장_실패2() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 50000);

        customerRepository.insert(customer);

        assertThrows(RuntimeException.class, () -> walletService.assignVoucher(customer, voucher));
    }

    @Test
    @Transactional
    @DisplayName("특정 고객의 바우처를 조회할 수 있다.")
    void 지갑_바우처_조회() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customer, voucher);

        //when
        List<Voucher> vouchers = walletService.searchVouchersByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchers, hasSize(1));
    }

    @Test
    @Transactional
    @DisplayName("특정 바우처를 지닌 고객을 조회할 수 있다.")
    void 지갑_고객_조회() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "hello@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 50000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customer, voucher);

        //when
        Customer findOne = walletService.searchCustomerByVoucherId(voucher.getVoucherId());

        //then
        assertEquals(customer, findOne);
    }

    @Test
    @Transactional
    @DisplayName("특정 고객의 바우처를 삭제할 수 있다.")
    void 지갑_바우처_삭제() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "world@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), PercentDiscount, 5);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customer, voucher);

        //when
        walletService.removeCustomerVoucher(customer, voucher);

        //then
        List<Voucher> wallet = customer.getWallet();
        assertThat(wallet.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @DisplayName("회원의 지갑에 없는 바우처는 삭제할 수 없다.")
    void 지갑_바우처_삭제_실패1() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "world@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), PercentDiscount, 5);
        Voucher another = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 10000);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customer, voucher);

        //when, then
        assertThrows(RuntimeException.class, () -> walletService.removeCustomerVoucher(customer, another));
    }

    @Test
    @Transactional
    @DisplayName("미등록 회원의 바우처는 삭제할 수 없다.")
    void 지갑_바우처_삭제_실패2() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "world@gmail.com", LocalDateTime.now());
        Customer stranger = new Customer(UUID.randomUUID(), "stranger", "bad@gmail.com", LocalDateTime.now());
        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), PercentDiscount, 5);

        customerRepository.insert(customer);
        voucherRepository.registerVoucher(voucher);
        walletService.assignVoucher(customer, voucher);

        //when, then
        assertThrows(RuntimeException.class, () -> walletService.removeCustomerVoucher(stranger, voucher));

    }
}