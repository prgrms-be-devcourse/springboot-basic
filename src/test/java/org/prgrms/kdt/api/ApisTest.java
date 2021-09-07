package org.prgrms.kdt.api;

import static org.prgrms.kdt.api.Apis.CUSTOMER;
import static org.prgrms.kdt.api.Apis.PRE_FIX;
import static org.prgrms.kdt.api.Apis.VOUCHER;
import static org.prgrms.kdt.api.Apis.WALLET;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseApiTest;
import org.prgrms.kdt.wallet.Wallet;
import org.prgrms.kdt.wallet.WalletDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:42 오후
 */

public class ApisTest extends BaseApiTest {

    @Test
    @DisplayName("지갑 등록 테스트")
    void addVoucherByCustomer() throws Exception {
        initCustomer();
        initVoucher();
        WalletDto mockWalletDto = givenValidWalletDto(mockCustomerId, mockVoucherId);

        mockMvc.perform(post(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(mockWalletDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("customerId").value(mockCustomerId.toString()))
                .andExpect(jsonPath("voucherId").value(mockVoucherId.toString()));
    }

    @Test
    @DisplayName("지갑 등록 예외 테스트 (고객의 아이디가 잘못된 경우)")
    void addVoucherByCustomer_notfound_customer() throws Exception {
        initVoucher();
        WalletDto dto = givenValidWalletDto(mockCustomerId, mockVoucherId);

        mockMvc.perform(post(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("지갑 등록 예외 테스트 (바우처 아이디가 잘못된 경우)")
    void addVoucherByCustomer_notfound_voucher() throws Exception {
        initCustomer();
        WalletDto dto = givenValidWalletDto(mockCustomerId, mockVoucherId);

        mockMvc.perform(post(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("고객 조회 테스트")
    void getVouchersByCustomerId() throws Exception {
        initCustomer();
        initVoucher();
        initWallet();

        mockMvc.perform(get(PRE_FIX + CUSTOMER, mockCustomerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.vouchers").exists())
                .andExpect(jsonPath("$..voucherId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..discount").exists())
                .andExpect(jsonPath("$..voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists());
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void getCustomersByVoucherId() throws Exception {
        initCustomer();
        initVoucher();
        initWallet();

        mockMvc.perform(get(PRE_FIX + VOUCHER, mockVoucherId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.discount").exists())
                .andExpect(jsonPath("$.voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$..customerId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..email").exists())
                .andExpect(jsonPath("$..createdAt").exists());
    }

    @Test
    @DisplayName("지갑 삭제 테스트")
    void deleteCustomersVoucher() throws Exception {
        initCustomer();
        initVoucher();

        WalletDto dto = givenValidWalletDto(mockCustomerId, mockVoucherId);

        mockMvc.perform(delete(PRE_FIX + WALLET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private WalletDto givenValidWalletDto(UUID mockCustomerId, UUID mockVoucherId) {
        WalletDto mockWalletDto = new WalletDto();
        mockWalletDto.setCustomerId(mockCustomerId.toString());
        mockWalletDto.setVoucherId(mockVoucherId.toString());
        return mockWalletDto;
    }
}