package com.tangerine.voucher_system.application.wallet.controller;

import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.voucher.controller.dto.VoucherResponse;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
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
class WalletRestControllerTest {
    @InjectMocks
    WalletRestController controller;
    @Mock
    WalletService service;

    @Test
    @DisplayName("지갑 생성하면 성공한다.")
    void createWallet_ParamWallet_CreateWallet() {
        doNothing().when(service).createWallet(any());

        controller.createWallet(new CreateWalletRequest(UUID.randomUUID(), UUID.randomUUID()));

        verify(service).createWallet(any(WalletParam.class));
    }

    @Test
    @DisplayName("지갑 갱신하면 성공한다.")
    void updateWallet_ParamWallet_UpdateWallet() {
        doNothing().when(service).updateWallet(any());

        controller.updateWallet(new UpdateWalletRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()));

        verify(service).updateWallet(any(WalletParam.class));
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
    void voucherListOfCustomer_ParamCustomerId_ReturnVoucherList(CustomerResult customer) {
        given(service.findVouchersByCustomerId(any())).willReturn(voucherResults);

        ResponseEntity<List<VoucherResponse>> result = controller.voucherListOfCustomer(customer.customerId());

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("바우처를 소유한 모든 고객을 바우처 아이디로 찾는다.")
    @MethodSource("provideVouchers")
    void customerHasVoucher_ParamVoucherId_ReturnCustomer(VoucherResult voucher) {
        given(service.findCustomersByVoucherId(any())).willReturn(customerResults);

        ResponseEntity<List<CustomerResponse>> result = controller.customerListHasVoucher(voucher.voucherId());

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotEmpty();
    }

    static List<VoucherResult> voucherResults = List.of(
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 100), LocalDate.now()),
            new VoucherResult(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 10), LocalDate.now()),
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 41), LocalDate.now())
    );

    static List<CustomerResult> customerResults = List.of(
            new CustomerResult(UUID.randomUUID(), new Name("사과"), false),
            new CustomerResult(UUID.randomUUID(), new Name("딸기"), true),
            new CustomerResult(UUID.randomUUID(), new Name("배"), false)
    );

    static Stream<Arguments> provideVouchers() {
        return voucherResults.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideCustomers() {
        return customerResults.stream()
                .map(Arguments::of);
    }

}