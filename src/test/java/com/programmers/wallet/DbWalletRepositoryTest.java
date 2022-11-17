package com.programmers.wallet;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
@Transactional
class DbWalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;

    Customer customer;
    Voucher voucher;

    @BeforeEach
    void 테스트용_데이터() {
        customer = new Customer(UUID.randomUUID(), "kiseo", "test@aaa.aaa", LocalDateTime.now());
        customerRepository.insert(customer);

        voucher = VoucherFactory.createVoucher(UUID.randomUUID(), PercentDiscount, 50);
        voucherRepository.registerVoucher(voucher);
    }

    @Test
    @DisplayName("DB에 바우처 지갑 정보가 성공적으로 저장된다.")
    void 바우처_지갑_저장_테스트() {
        assertDoesNotThrow(() -> walletRepository.assignVoucher(customer, voucher));
    }

    @Test
    @DisplayName("특정 바우처를 보유한 회원을 검색할 수 있다.")
    void 바우처_회원_조회_테스트() {
        walletRepository.assignVoucher(customer, voucher);

        Optional<Customer> voucherOwner = walletRepository.findCustomerByVoucherId(voucher.getVoucherId());
        assertTrue(voucherOwner.isPresent());

        assertEquals(customer, voucherOwner.get());
    }

    @Test
    @DisplayName("특정 회원의 바우처 목록을 조회할 수 있다.")
    void 회원_바우처_조회_테스트() {
        Customer customer = new Customer(UUID.randomUUID(), "kiseo", "giseo@aaa.aaa", LocalDateTime.now());
        customerRepository.insert(customer);

        Voucher voucher = VoucherFactory.createVoucher(UUID.randomUUID(), FixedAmount, 50000);
        voucherRepository.registerVoucher(voucher);
        Voucher voucher2 = VoucherFactory.createVoucher(UUID.randomUUID(), PercentDiscount, 5);
        voucherRepository.registerVoucher(voucher2);

        walletRepository.assignVoucher(customer, voucher);
        walletRepository.assignVoucher(customer, voucher2);


        List<Voucher> result = walletRepository.findVouchersByCustomerId(customer.getCustomerId());

        assertThat(result.isEmpty(), is(false));
        assertThat(result, hasSize(2));
    }

    @Test
    @DisplayName("특정 고객의 바우처를 지갑에서 제거할 수 있다.")
    void 지갑_바우처_제거_테스트() {
        walletRepository.assignVoucher(customer, voucher);

        List<Voucher> beforeDelete = walletRepository.findVouchersByCustomerId(customer.getCustomerId());
        assertThat(beforeDelete.isEmpty(), is(false));
        assertThat(beforeDelete, hasSize(1));

        walletRepository.deleteCustomerVoucher(customer.getCustomerId(), voucher.getVoucherId());
        List<Voucher> afterDelete = walletRepository.findVouchersByCustomerId(customer.getCustomerId());

        assertThat(afterDelete.isEmpty(), is(true));
    }
}