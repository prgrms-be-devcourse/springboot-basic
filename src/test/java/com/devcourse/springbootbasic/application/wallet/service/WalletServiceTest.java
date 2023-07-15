package com.devcourse.springbootbasic.application.wallet.service;

import com.devcourse.springbootbasic.application.customer.model.Customer;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import com.devcourse.springbootbasic.application.wallet.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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
class WalletServiceTest {

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), Optional.of(UUID.randomUUID())),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), Optional.of(UUID.randomUUID())),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 41), Optional.of(UUID.randomUUID()))
    );
    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "사과", false),
            new Customer(UUID.randomUUID(), "딸기", true),
            new Customer(UUID.randomUUID(), "배", false)
    );
    @InjectMocks
    WalletService service;
    @Mock
    WalletRepository repository;

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
    @DisplayName("바우처 아이디에 맞는 바우처에 고객 아이디를 업데이트한다.")
    void updateCustomerIdOfVoucher_ParamVoucherIdAndCustomerId_UpdateDone() {
        doNothing().when(repository).updateVoucher(any(), any());

        service.updateCustomerIdOfVoucher(any(UUID.class), any(UUID.class));

        verify(repository).updateVoucher(any(), any());
    }

    @Test
    @DisplayName("바우처 아이디에 고객 아이디를 null로 업데이트한다.")
    void updateCustomerIdNullOfVoucher_ParamVoucherId_UpdateDone() {
        doNothing().when(repository).updateVoucher(any(UUID.class));

        service.updateCustomerIdNullOfVoucher(any(UUID.class));

        verify(repository).updateVoucher(any());
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 소유한 바우처를 리스트로 반환한다.")
    @MethodSource("provideVouchers")
    void findVouchersByCustomerId_ParamCustomerId_ReturnVoucher(Voucher voucher) {
        given(repository.findAllVouchersByCustomerId(any())).willReturn(vouchers);

        List<Voucher> result = service.findVouchersByCustomerId(voucher.getCustomerId().get());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("바우처 아이디를 가진 고객을 찾는다.")
    @MethodSource("provideDataSet")
    void findCustomerByVoucherId_ParamVoucherId_ReturnCustomer(Voucher voucher, Customer customer) {
        given(repository.findCustomerByVoucherId(any())).willReturn(Optional.of(customer));

        Customer result = service.findCustomerByVoucherId(voucher.getVoucherId());

        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @ParameterizedTest
    @DisplayName("바우처 타입을 가진 모든 고객을 찾는다.")
    @MethodSource("provideVouchers")
    void findCustomersByVoucherType_ParamVoucherType_ReturnCustomerList(Voucher voucher) {
        given(repository.findAllCustomersByVoucherType(voucher.getVoucherType())).willReturn(customers);

        List<Customer> result = service.findCustomersByVoucherType(voucher.getVoucherType());

        assertThat(result).isNotEmpty();
    }
}