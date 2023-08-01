package com.tangerine.voucher_system.application.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangerine.voucher_system.application.customer.controller.mapper.CustomerControllerMapper;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.global.exception.ResponseMessage;
import com.tangerine.voucher_system.application.global.generator.IdGenerator;
import com.tangerine.voucher_system.application.voucher.controller.mapper.VoucherControllerMapper;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.mapper.WalletControllerMapper;
import com.tangerine.voucher_system.application.wallet.service.WalletService;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletRestController.class)
@Import({WalletControllerMapper.class, VoucherControllerMapper.class, CustomerControllerMapper.class, IdGenerator.class})
class WalletRestControllerTest {

    @MockBean
    WalletService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest
    @DisplayName("지갑 생성하면 성공한다.")
    @MethodSource("provideWallets")
    void createWallet_ParamWallet_CreateWallet(WalletResult result) throws Exception {
        given(service.createWallet(any(WalletParam.class)))
                .willReturn(result.walletId());

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateWalletRequest(result.voucherId(), result.customerId()))))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$")
                        .value(result.walletId().toString() + ResponseMessage.CREATE_SUCCESS));

        verify(service, times(1)).createWallet(any());
    }

    @ParameterizedTest
    @DisplayName("지갑 갱신하면 성공한다.")
    @MethodSource("provideWallets")
    void updateWallet_ParamWallet_UpdateWallet(WalletResult result) throws Exception {
        given(service.updateWallet(any())).willReturn(result.walletId());

        mockMvc.perform(patch("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateWalletRequest(result.voucherId(), result.customerId()))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$")
                        .value(result.walletId().toString() + ResponseMessage.UPDATE_SUCCESS));

        verify(service, times(1)).updateWallet(any());
    }

    @ParameterizedTest
    @DisplayName("지갑 삭제하면 성공한다.")
    @MethodSource("provideWallets")
    void deleteWalletById_ParamWalletId_DeleteWallet(WalletResult result) throws Exception {
        given(service.deleteWalletById(any())).willReturn(result.walletId());

        mockMvc.perform(delete("/api/v1/wallets")
                        .param("id", result.walletId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$")
                        .value(result.walletId().toString() + ResponseMessage.DELETE_SUCCESS));

        verify(service, times(1)).deleteWalletById(any());
    }

    @ParameterizedTest
    @DisplayName("고객 아이디로 할당된 모든 바우처를 반환한다.")
    @MethodSource("provideCustomers")
    void voucherListOfCustomer_ParamCustomerId_ReturnVoucherList(CustomerResult customer) throws Exception {
        given(service.findVouchersByCustomerId(any())).willReturn(voucherResults);

        mockMvc.perform(get("/api/v1/wallets/customer")
                        .param("id", customer.customerId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responses[0].voucherId").value(voucherResults.get(0).voucherId().toString()));

        verify(service, times(1)).findVouchersByCustomerId(any());
    }

    @ParameterizedTest
    @DisplayName("바우처를 소유한 모든 고객을 바우처 아이디로 찾는다.")
    @MethodSource("provideVouchers")
    void customerHasVoucher_ParamVoucherId_ReturnCustomer(VoucherResult voucher) throws Exception {
        given(service.findCustomersByVoucherId(any())).willReturn(customerResults);

        mockMvc.perform(get("/api/v1/wallets/voucher")
                        .param("id", voucher.voucherId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responses[0].customerId").value(customerResults.get(0).customerId().toString()));

        verify(service, times(1)).findCustomersByVoucherId(any());
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

    static List<WalletResult> walletResults = List.of(
            new WalletResult(UUID.randomUUID(), voucherResults.get(0).voucherId(), customerResults.get(0).customerId()),
            new WalletResult(UUID.randomUUID(), voucherResults.get(1).voucherId(), customerResults.get(1).customerId()),
            new WalletResult(UUID.randomUUID(), voucherResults.get(2).voucherId(), customerResults.get(2).customerId())
    );

    static Stream<Arguments> provideVouchers() {
        return voucherResults.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideCustomers() {
        return customerResults.stream()
                .map(Arguments::of);
    }

    static Stream<Arguments> provideWallets() {
        return walletResults.stream()
                .map(Arguments::of);
    }

}