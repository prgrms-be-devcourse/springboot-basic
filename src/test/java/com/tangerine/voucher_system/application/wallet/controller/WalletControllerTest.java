package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.voucher.controller.VoucherDto;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig
class WalletControllerTest {
    @InjectMocks
    WalletController controller;
    @Mock
    WalletService service;

    @Test
    @DisplayName("지갑 생성하면 성공한다.")
    void createWallet_ParamWallet_CreateWallet() {
        doNothing().when(service).createWallet(any());

        controller.createWallet(new Wallet(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));

        verify(service).createWallet(any(Wallet.class));
    }

    @Test
    @DisplayName("지갑 갱신하면 성공한다.")
    void updateWallet_ParamWallet_UpdateWallet() {
        doNothing().when(service).updateWallet(any());

        controller.updateWallet(new Wallet(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));

        verify(service).updateWallet(any(Wallet.class));
    }

    @Test
    @DisplayName("지갑 삭제하면 성공한다.")
    void deleteWalletById_ParamWalletId_DeleteWallet() {
        doNothing().when(service).deleteWalletById(any());

        controller.deleteWalletById(UUID.randomUUID());

        verify(service).deleteWalletById(any(UUID.class));
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 할당된 모든 바우처를 반환한다.")
    @MethodSource("provideCustomers")
    void voucherListOfCustomer_ParamCustomerId_ReturnVoucherList(Customer customer) {
        given(service.findVouchersByCustomerId(any())).willReturn(vouchers);

        List<VoucherDto> result = controller.voucherListOfCustomer(customer.customerId());

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("바우처를 소유한 모든 고객을 바우처 아이디로 찾는다.")
    @MethodSource("provideVouchers")
    void customerHasVoucher_ParamVoucherId_ReturnCustomer(Voucher voucher) {
        given(service.findCustomersByVoucherId(any())).willReturn(customers);

        List<CustomerDto> result = controller.customerListHasVoucher(voucher.getVoucherId());

        assertThat(result).isNotEmpty();
    }

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDate.now()),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), LocalDate.now()),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 41), LocalDate.now())
    );

    static List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), new Name("사과"), false),
            new Customer(UUID.randomUUID(), new Name("딸기"), true),
            new Customer(UUID.randomUUID(), new Name("배"), false)
    );

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideCustomers() {
        return customers.stream()
                .map(Arguments::of);
    }

}