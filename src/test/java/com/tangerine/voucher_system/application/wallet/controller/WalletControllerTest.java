package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.CustomerDto;
import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.voucher.controller.VoucherDto;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig
class WalletControllerTest {

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDateTime.now(), Optional.empty()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), LocalDateTime.now(), Optional.empty()),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 41), LocalDateTime.now(), Optional.empty())
    );
    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), new Name("사과"), false),
            new Customer(UUID.randomUUID(), new Name("딸기"), true),
            new Customer(UUID.randomUUID(), new Name("배"), false)
    );
    @InjectMocks
    WalletController controller;
    @Mock
    WalletService service;

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

    @Test
    @DisplayName("바우처에 고객 아이디를 할당하는 경우 성공한다.")
    void assignVoucherToCustomer_ParamVoucherIdAndCustomerId_AssignDone() {
        doNothing().when(service).updateCustomerIdOfVoucher(any(), any());

        controller.assignVoucherToCustomer(UUID.randomUUID(), UUID.randomUUID());

        verify(service).updateCustomerIdOfVoucher(any(), any());
    }

    @Test
    @DisplayName("바우처에 고객 아이디를 할당하는 경우 성공한다.")
    void withdrawVoucherToCustomer_ParamVoucherIdAndCustomerId_WithdrawDone() {
        doNothing().when(service).updateCustomerIdNullOfVoucher(any());

        controller.withdrawVoucherFromCustomer(UUID.randomUUID());

        verify(service).updateCustomerIdNullOfVoucher(any());
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 할당된 모든 바우처를 반환한다.")
    @MethodSource("provideCustomers")
    void voucherListOfCustomer_ParamCustomerId_ReturnVoucherList(Customer customer) {
        given(service.findVouchersByCustomerId(any())).willReturn(vouchers);

        List<VoucherDto> result = controller.voucherListOfCustomer(customer.getCustomerId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("바우처를 소유한 고객을 바우처 아이디로 찾는다.")
    @MethodSource("provideDataSet")
    void customerHasVoucher_ParamVoucherId_ReturnCustomer(Voucher voucher, Customer customer) {
        given(service.findCustomerByVoucherId(any())).willReturn(customer);

        CustomerDto result = controller.customerHasVoucher(voucher.getVoucherId());

        assertThat(result.customerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("바우처 타입을 기준으로 해당 바우처를 가진 모든 고객을 찾는다.")
    @MethodSource("provideVouchers")
    void customerListHasVoucherType_ParamVoucherType_ReturnCustomerList(Voucher voucher) {
        given(service.findCustomersByVoucherType(any())).willReturn(customers);

        List<CustomerDto> result = controller.customerListHasVoucherType(voucher.getVoucherType());

        assertThat(result).isNotEmpty();
    }
}