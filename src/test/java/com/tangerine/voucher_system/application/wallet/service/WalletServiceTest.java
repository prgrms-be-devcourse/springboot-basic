package com.tangerine.voucher_system.application.wallet.service;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class WalletServiceTest {

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
    WalletService service;
    @Autowired
    VoucherService voucherService;
    @Autowired
    CustomerService customerService;

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideDataSet() {
        return Stream.of(
                Arguments.of(vouchers.get(0), customers.get(0)),
                Arguments.of(vouchers.get(1), customers.get(1)),
                Arguments.of(vouchers.get(2), customers.get(2))
        );
    }

    @BeforeEach
    void setup() {
        vouchers.forEach(voucherService::createVoucher);
        customers.forEach(customerService::createCustomer);
    }

    @AfterEach
    void cleanup() {
        voucherService.deleteAllVouchers();
        customerService.deleteAllCustomers();
    }

    @ParameterizedTest
    @DisplayName("바우처 아이디에 맞는 바우처에 고객 아이디를 업데이트한다.")
    @MethodSource("provideDataSet")
    void updateCustomerIdOfVoucher_ParamVoucherIdAndCustomerId_UpdateDone(Voucher voucher, Customer customer) {

        service.updateCustomerIdOfVoucher(voucher.getVoucherId(), customer.getCustomerId());

        Voucher result = voucherService.findVoucherById(voucher.getVoucherId());
        assertThat(result.getCustomerId()).isNotEmpty();
        assertThat(result.getCustomerId()).contains(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("바우처 아이디에 고객 아이디를 null 로 업데이트한다.")
    @MethodSource("provideVouchers")
    void updateCustomerIdNullOfVoucher_ParamVoucherId_UpdateDone(Voucher voucher) {

        service.updateCustomerIdNullOfVoucher(voucher.getVoucherId());

        Voucher result = voucherService.findVoucherById(voucher.getVoucherId());
        assertThat(result.getCustomerId()).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 소유한 바우처를 리스트로 반환한다.")
    @MethodSource("provideDataSet")
    void findVouchersByCustomerId_ParamCustomerId_ReturnVoucher(Voucher voucher, Customer customer) {
        service.updateCustomerIdOfVoucher(voucher.getVoucherId(), customer.getCustomerId());

        List<Voucher> result = service.findVouchersByCustomerId(customer.getCustomerId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("바우처 아이디를 가진 고객을 찾는다.")
    @MethodSource("provideDataSet")
    void findCustomerByVoucherId_ParamVoucherId_ReturnCustomer(Voucher voucher, Customer customer) {
        service.updateCustomerIdOfVoucher(voucher.getVoucherId(), customer.getCustomerId());

        Customer result = service.findCustomerByVoucherId(voucher.getVoucherId());

        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("바우처 타입을 가진 모든 고객을 찾는다.")
    @MethodSource("provideDataSet")
    void findCustomersByVoucherType_ParamVoucherType_ReturnCustomerList(Voucher voucher, Customer customer) {
        service.updateCustomerIdOfVoucher(voucher.getVoucherId(), customer.getCustomerId());

        List<Customer> result = service.findCustomersByVoucherType(voucher.getVoucherType());

        assertThat(result).isNotEmpty();
    }
}