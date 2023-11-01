package org.prgrms.vouchermanager.handler.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.vouchermanager.domain.wallet.ApiWalletRequestDto;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
import org.prgrms.vouchermanager.service.VoucherService;
import org.prgrms.vouchermanager.service.WalletService;
import org.prgrms.vouchermanager.testdata.WalletData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiWalletController.class)
@Slf4j
class ApiWalletControllerTest {
    @MockBean
    private WalletService service;
    @MockBean
    private VoucherService voucherService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("지갑 생성 api 호출에 성공한다")
    public void create() throws Exception {
        WalletRequestDto walletDto = WalletData.getWalletDto();
        ApiWalletRequestDto apiDto = ApiWalletRequestDto.builder().customerEmail(walletDto.getCustomerEmail()).voucherId(walletDto.getVoucher().getVoucherId()).build();
        when(service.createWallet(any(WalletRequestDto.class))).thenReturn(walletDto);
        when(voucherService.findById(any(UUID.class))).thenReturn(Optional.ofNullable(walletDto.getVoucher()));
        mvc.perform(post("/api/wallets/create")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(apiDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.customerEmail").value(walletDto.getCustomerEmail()))
                .andExpect(jsonPath("$.data.voucher.voucherId").value(walletDto.getVoucher().getVoucherId().toString()));



    }
    @Test
    @DisplayName("이메일로 지갑 조회 api 호출에 성공한다")
    public void findByEmail() throws Exception {
        Wallet wallet = WalletData.getWallet();
        when(service.findByEmail(any(String.class))).thenReturn(wallet);
        mvc.perform(get("/api/wallets/{email}", "dlswns661035@gmail.com"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.walletId").value(wallet.getWalletId()))
                .andExpect(jsonPath("$.data.customerEmail").value(wallet.getCustomerEmail()))
                .andExpect(jsonPath("$.data.voucherId").value(wallet.getVoucherId().toString()));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}