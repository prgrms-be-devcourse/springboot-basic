package com.programmers.springbootbasic.wallet.repository;

import com.programmers.springbootbasic.customer.domain.Customer;
import com.programmers.springbootbasic.customer.repository.JdbcCustomerRepository;
import com.programmers.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.voucher.domain.PercentDiscountVoucher;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.repository.JdbcVoucherRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JdbcWalletRepository.class, JdbcVoucherRepository.class, JdbcCustomerRepository.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcWalletRepositoryTest {
    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    private JdbcWalletRepository jdbcWalletRepository;

    @Transactional
    @DisplayName("특정 회원에게 바우처를 할당한다")
    @Test
    void updateVoucherCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "voucherName", 10L);
        Customer customer = new Customer("customerName");

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcCustomerRepository.save(customer);

        //when
        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        UUID assignedCustomerId = jdbcWalletRepository.findCustomerByVoucherId(fixedAmountVoucher.getVoucherId()).get().getCustomerId();
        //then
        assertThat(assignedCustomerId, is(customer.getCustomerId()));
    }

    @Transactional
    @DisplayName("고객이 보유하고 있는 바우처를 조회한다")
    @Test
    void findVouchersByCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);
        PercentDiscountVoucher percentDiscountVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), "testName3", 40L);
        Customer customer = new Customer("customerName");

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher2);
        jdbcCustomerRepository.save(customer);

        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), percentDiscountVoucher.getVoucherId());

        //when
        List<Voucher> vouchers = jdbcWalletRepository.findVouchersByCustomerId(customer.getCustomerId());

        //then
        assertThat(vouchers.size(), is(2));
    }

    @Transactional
    @DisplayName("특정 바우처를 보유한 고객을 조회한다")
    @Test
    void findCustomerByVoucherId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        Customer customer = new Customer("customerName");

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcCustomerRepository.save(customer);
        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());

        //when
        Optional<Customer> resultCustomer = jdbcWalletRepository.findCustomerByVoucherId(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(resultCustomer.get().getCustomerId(), is(customer.getCustomerId()));
    }

    @Transactional
    @DisplayName("특정 바우처를 보유한 고객을 찾을 수 없는 경우 빈 Customer를 반환한다")
    @Test
    void findCustomerByVoucherIdEmpty() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        jdbcVoucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Customer> customer = jdbcWalletRepository.findCustomerByVoucherId(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(customer).isEmpty();
    }

    @Transactional
    @DisplayName("회원이 보유한 특정 바우처를 제거한다")
    @Test
    void deleteVoucherByVoucherIdAndCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);
        Customer customer = new Customer("customerName");

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);
        jdbcCustomerRepository.save(customer);

        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), percentDiscountVoucher.getVoucherId());

        //when
        jdbcWalletRepository.deleteVoucherByVoucherIdAndCustomerId(fixedAmountVoucher.getVoucherId(), customer.getCustomerId());
        List<Voucher> result = jdbcVoucherRepository.findAll();

        //then
        assertThat(result.size(), is(1));
    }

    @Transactional
    @DisplayName("회원이 보유한 모든 바우처를 제거한다")
    @Test
    void deleteAllVouchersByCustomerId() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);
        Customer customer = new Customer("customerName");

        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountVoucher);
        jdbcCustomerRepository.save(customer);

        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), fixedAmountVoucher.getVoucherId());
        jdbcWalletRepository.updateVoucherCustomerId(customer.getCustomerId(), percentDiscountVoucher.getVoucherId());

        //when
        jdbcWalletRepository.deleteAllVouchersByCustomerId(customer.getCustomerId());
        List<Voucher> result = jdbcWalletRepository.findVouchersByCustomerId(customer.getCustomerId());

        //then
        assertThat(result).isEmpty();
    }
}