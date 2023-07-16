package com.tengerine.voucher_system.application.wallet.repository;

import com.tengerine.voucher_system.application.customer.model.Customer;
import com.tengerine.voucher_system.application.customer.repository.CustomerRepository;
import com.tengerine.voucher_system.application.customer.repository.JdbcCustomerRepository;
import com.tengerine.voucher_system.application.voucher.model.DiscountValue;
import com.tengerine.voucher_system.application.voucher.model.Voucher;
import com.tengerine.voucher_system.application.voucher.model.VoucherType;
import com.tengerine.voucher_system.application.voucher.repository.JdbcVoucherRepository;
import com.tengerine.voucher_system.application.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({JdbcWalletRepository.class, JdbcCustomerRepository.class, JdbcVoucherRepository.class})
class JdbcWalletRepositoryTest {

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDateTime.now(), Optional.empty()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), LocalDateTime.now(), Optional.empty()),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 41), LocalDateTime.now(), Optional.empty())
    );
    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "배", false)
    );
    @Autowired
    WalletRepository repository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VoucherRepository voucherRepository;

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideCustomers() {
        return customers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideDataSet() {
        return Stream.of(
                Arguments.of(vouchers.get(0), customers.get(0)),
                Arguments.of(vouchers.get(1), customers.get(1)),
                Arguments.of(vouchers.get(2), customers.get(2))
        );
    }

    @BeforeAll
    void init() {
        customers.forEach(customer -> customerRepository.insert(customer));
        vouchers.forEach(voucher -> voucherRepository.insert(voucher));
    }

    @Order(1)
    @ParameterizedTest
    @DisplayName("바우처, 고객 아이디를 이용해서 바우처를 고객에게 할당한다.")
    @MethodSource("provideDataSet")
    void updateVoucher_ParamVoucherIdAndCustomerId_UpdateVoucher(Voucher voucher, Customer customer) {
        repository.updateVoucher(voucher.getVoucherId(), customer.getCustomerId());

        List<Voucher> vouchersOfCustomer = repository.findAllVouchersByCustomerId(customer.getCustomerId());
        assertThat(vouchersOfCustomer).isNotEmpty();
    }

    @Order(5)
    @ParameterizedTest
    @DisplayName("바우처 아이디를 이용해서 할당된 고객을 해제한다.")
    @MethodSource("provideDataSet")
    void updateVoucher_ParamVoucherIdAndNull_UpdateVoucher(Voucher voucher, Customer customer) {
        repository.updateVoucher(voucher.getVoucherId());

        List<Voucher> result = repository.findAllVouchersByCustomerId(customer.getCustomerId());
        assertThat(result).isEmpty();
    }

    @Order(2)
    @ParameterizedTest
    @DisplayName("고객 아이디로 고객에게 할당된 모든 바우처를 반환한다.")
    @MethodSource("provideCustomers")
    void findAllVouchersByCustomerId_ParamCustomerId_ReturnVoucherList(Customer customer) {
        repository.updateVoucher(vouchers.get(0).getVoucherId(), customer.getCustomerId());

        List<Voucher> result = repository.findAllVouchersByCustomerId(customer.getCustomerId());

        assertThat(result).isNotEmpty();
    }

    @Order(3)
    @ParameterizedTest
    @DisplayName("바우처 아이디로 바우처가 할당된 고객 정보를 반환한다.")
    @MethodSource("provideVouchers")
    void findCustomerByVoucherId_ParamVoucherId_ReturnCustomer(Voucher voucher) {
        repository.updateVoucher(voucher.getVoucherId(), customers.get(0).getCustomerId());

        Optional<Customer> result = repository.findCustomerByVoucherId(voucher.getVoucherId());

        assertThat(result).isNotEmpty();
    }

    @Order(4)
    @ParameterizedTest
    @DisplayName("바우처 타입에 해당하는 바우처를 가진 모든 고객을 반환한다.")
    @MethodSource("provideVouchers")
    void findAllCustomersByVoucherType_ParamVoucherType_ReturnCustomerList(Voucher voucher) {
        repository.updateVoucher(voucher.getVoucherId(), customers.get(0).getCustomerId());

        System.out.println(repository.findAllVouchersByCustomerId(customers.get(0).getCustomerId()));

        List<Customer> result = repository.findAllCustomersByVoucherType(voucher.getVoucherType());

        assertThat(result).isNotEmpty();
    }

}