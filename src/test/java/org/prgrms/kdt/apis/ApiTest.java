package org.prgrms.kdt.apis;

import static org.hamcrest.Matchers.hasSize;
import static org.prgrms.kdt.apis.Api.CUSTOMER;
import static org.prgrms.kdt.apis.Api.PRE_FIX;
import static org.prgrms.kdt.apis.Api.SEARCH;
import static org.prgrms.kdt.apis.Api.VOUCHER;
import static org.prgrms.kdt.apis.Api.VOUCHERS;
import static org.prgrms.kdt.apis.Api.WALLET;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseApiTest;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherDto;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.wallet.WalletDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:42 오후
 */

public class ApiTest extends BaseApiTest {

    @Test
    @DisplayName("고객에게 바우처를 정상적으로 할당하는 테스트")
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
    @DisplayName("고객에게 바우처 할당시 고객이 존재하지 않으면 not found 테스트")
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
    @DisplayName("고객에게 바우처 할당시 바우처가 존재하지 않으면 not found 테스트")
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
    @DisplayName("고객의 아이디로 보유한 바우처 조회 테스트")
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
    @DisplayName("고객의 아이디로 보유한 바우처 조회 테스트")
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
    @DisplayName("고객의 바우처 삭제 테스트")
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

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void getAllVouchers() throws Exception {
        initVoucher();
        initVoucher();
        initVoucher();

        mockMvc.perform(get(PRE_FIX + VOUCHERS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..voucherId").exists())
                .andExpect(jsonPath("$..discount").exists())
                .andExpect(jsonPath("$..voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists());
    }

    @Test
    @DisplayName("바우처 등록 테스트")
    void addVoucher() throws Exception {
        mockMvc.perform(post(PRE_FIX + VOUCHERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(givenVoucherDto())))
                .andDo(print())
                .andExpect(jsonPath("$..voucherId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..discount").exists())
                .andExpect(jsonPath("$..voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists());

    }

    @Test
    @DisplayName("바우처 삭제 테스트")
    void deleteVoucher() throws Exception {
        UUID id = UUID.randomUUID();
        VoucherDto dto = givenVoucherDto();
        dto.setVoucherId(id.toString());
        voucherRepository.insert(new Voucher(id, "test",100L, VoucherType.FIX, LocalDateTime.now()));

        mockMvc.perform(delete(PRE_FIX + VOUCHERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    @Test
    @DisplayName("FIX 타입 바우처 조회 테스트")
    void getVoucherByType() throws Exception {
        initVoucher();      // FIX
        initVoucher();      // FIX
        voucherRepository.insert(new Voucher(UUID.randomUUID(), "test",100L, VoucherType.PERCENT, LocalDateTime.now()));

        mockMvc.perform(get(PRE_FIX + VOUCHERS + SEARCH + "/type?voucherType=FIX")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$..voucherId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..discount").exists())
                .andExpect(jsonPath("$..voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists());
    }

    @Test
    @DisplayName("존재하지 안는 바우처 조회시 404 응답 테스트")
    void getVoucherByType_badRequest() throws Exception {
        mockMvc.perform(get(PRE_FIX + VOUCHERS + SEARCH + "/type?voucherType=NOOOOOOP")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("바우처 기간별 조회 테스트")
    void getByCreatedAt() throws Exception {
        voucherRepository.insert(new Voucher(UUID.randomUUID(), "test", 100L, VoucherType.FIX, LocalDateTime.of(2020, 11, 12, 00, 00, 00)));

        mockMvc.perform(get(PRE_FIX + VOUCHERS + SEARCH + "/createAt?beforeDate=2020-01-01&afterDate=2022-01-01"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$..voucherId").exists())
                .andExpect(jsonPath("$..name").exists())
                .andExpect(jsonPath("$..discount").exists())
                .andExpect(jsonPath("$..voucherType").exists())
                .andExpect(jsonPath("$..createdAt").exists());
    }

    private VoucherDto givenVoucherDto() {
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setName("test voucher");
        voucherDto.setVoucherType("FIX");
        voucherDto.setDiscount("1000");
        return voucherDto;
    }

    private WalletDto givenValidWalletDto(UUID mockCustomerId, UUID mockVoucherId) {
        WalletDto mockWalletDto = new WalletDto();
        mockWalletDto.setCustomerId(mockCustomerId.toString());
        mockWalletDto.setVoucherId(mockVoucherId.toString());
        return mockWalletDto;
    }
}