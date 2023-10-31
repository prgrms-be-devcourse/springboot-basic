package com.zerozae.voucher.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.wallet.Wallet;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.wallet.WalletCreateRequest;
import com.zerozae.voucher.dto.wallet.WalletResponse;
import com.zerozae.voucher.service.customer.CustomerService;
import com.zerozae.voucher.service.voucher.VoucherService;
import com.zerozae.voucher.service.wallet.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.domain.customer.CustomerType.BLACKLIST;
import static com.zerozae.voucher.domain.voucher.UseStatusType.AVAILABLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiWalletController.class)
class ApiWalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    WalletService walletService;

    @MockBean
    CustomerService customerService;

    @MockBean
    VoucherService voucherService;

    private Customer customer = new Customer(UUID.randomUUID(), "customer", BLACKLIST);
    private Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 10, AVAILABLE, LocalDate.now());
    private Wallet wallet = new Wallet(customer.getCustomerId(), voucher.getVoucherId());

    @Test
    @DisplayName("지갑 생성 테스트")
    void createWallet_Success_Test() throws Exception {
        // Given
        WalletCreateRequest request = new WalletCreateRequest(customer.getCustomerId().toString(), voucher.getVoucherId().toString());
        when(walletService.createWallet(request)).thenReturn(WalletResponse.toDto(wallet));

        // When
        mvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Then
        verify(walletService).createWallet(any(WalletCreateRequest.class));
    }

    @Test
    @DisplayName("지갑 전체 조회 테스트")
    void findAllWallets_Success_Test() throws Exception {
        // Given
        List<WalletResponse> wallets = List.of(
                WalletResponse.toDto(wallet)
        );
        when(walletService.findAllWallets()).thenReturn(wallets);

        // When
        mvc.perform(get("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].voucherId").value(voucher.getVoucherId().toString()));

        // Then
        verify(walletService).findAllWallets();
    }

    @Test
    @DisplayName("회원이 보유한 바우처 조회 테스트")
    void findWalletsByCustomerId_Success_Test() throws Exception {
        // Given
        UUID customerId = UUID.randomUUID();
        List<WalletResponse> wallets = List.of(
                WalletResponse.toDto(new Wallet(UUID.randomUUID(), UUID.randomUUID()))
        );
        VoucherResponse voucherResponse = VoucherResponse.toDto(voucher);

        when(walletService.findWalletByCustomerId(customerId)).thenReturn(wallets);
        when(voucherService.findById(any(UUID.class))).thenReturn(voucherResponse);

        // When
        mvc.perform(get("/api/wallets/customer/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        verify(walletService).findWalletByCustomerId(customerId);
        verify(voucherService, times(wallets.size())).findById(any(UUID.class));
    }

    @Test
    @DisplayName("바우처를 보유한 회원 조회 테스트")
    void findWalletsByVoucherId_Success_Test() throws Exception {
        // Given
        UUID voucherId = UUID.randomUUID();
        List<WalletResponse> wallets = List.of(
                WalletResponse.toDto(new Wallet(UUID.randomUUID(), UUID.randomUUID()))
        );
        CustomerResponse customerResponse = CustomerResponse.toDto(customer);

        when(walletService.findWalletByVoucherId(voucherId)).thenReturn(wallets);
        when(customerService.findById(any(UUID.class))).thenReturn(customerResponse);

        // When
        mvc.perform(get("/api/wallets/voucher/{voucherId}", voucherId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(walletService).findWalletByVoucherId(voucherId);
        verify(customerService, times(wallets.size())).findById(any(UUID.class));
    }

    @Test
    @DisplayName("지갑 삭제 테스트")
    void deleteWallet_Success_Test() throws Exception {
        // Given

        // When
        mvc.perform(delete("/api/wallets/{customerId}/{voucherId}", customer.getCustomerId(), voucher.getVoucherId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(walletService).deleteWalletFromCustomer(customer.getCustomerId(), voucher.getVoucherId());
    }
}
