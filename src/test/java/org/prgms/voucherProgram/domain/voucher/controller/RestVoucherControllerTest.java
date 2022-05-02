package org.prgms.voucherProgram.domain.voucher.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.prgms.voucherProgram.voucher.controller.RestVoucherController;
import org.prgms.voucherProgram.voucher.domain.FixedAmountVoucher;
import org.prgms.voucherProgram.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucherProgram.voucher.domain.Voucher;
import org.prgms.voucherProgram.voucher.dto.VoucherDto;
import org.prgms.voucherProgram.voucher.dto.VoucherFindRequest;
import org.prgms.voucherProgram.voucher.dto.VoucherRequest;
import org.prgms.voucherProgram.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RestVoucherController.class)
class RestVoucherControllerTest {

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("바우처를 생성한다.")
    @Test
    void createVoucher() throws Exception {
        // given
        VoucherRequest voucherRequest = voucherRequest();
        Voucher voucher = voucher();
        VoucherDto voucherDto = VoucherDto.from(voucher);

        given(voucherService.create(any(VoucherRequest.class))).willReturn(voucher);

        // when
        ResultActions resultActions = mockMVC.perform(post("/api/v1/vouchers")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(voucherRequest))
        );

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("voucherId", voucherDto.getVoucherId()).exists())
            .andExpect(jsonPath("type", voucherDto.getType()).exists())
            .andExpect(jsonPath("discountValue", voucherDto.getDiscountValue()).exists())
            .andExpect(jsonPath("createdDateTime", voucherDto.getCreatedDateTime()).exists());
    }

    @DisplayName("ID를 통해 바우처를 조회한다.")
    @Test
    void getVoucherWithId() throws Exception {
        // given
        Voucher voucher = voucher();
        VoucherDto voucherDto = VoucherDto.from(voucher);

        given(voucherService.findVoucher(any(UUID.class))).willReturn(voucher);

        // when
        ResultActions resultActions = mockMVC.perform(
            get("/api/v1/vouchers/{voucherId}", voucher.getVoucherId().toString())
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("voucherId", voucherDto.getVoucherId()).exists())
            .andExpect(jsonPath("type", voucherDto.getType()).exists())
            .andExpect(jsonPath("discountValue", voucherDto.getDiscountValue()).exists())
            .andExpect(jsonPath("createdDateTime", voucherDto.getCreatedDateTime()).exists());
    }

    @DisplayName("모든 바우처 목록을 조회한다.")
    @Test
    void getVouchers() throws Exception {
        // given
        given(voucherService.findAllVoucher()).willReturn(vouchers());

        // when
        ResultActions resultActions = mockMVC.perform(
            get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        VoucherDto[] voucherDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            VoucherDto[].class);
        assertThat(voucherDtos).hasSize(2);
    }

    @DisplayName("생성기간과 할인타입을 통해 바우처를 조회한다.")
    @Test
    void getVoucherWithCreatedAndType() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();
        List<Voucher> vouchers = List.of(new FixedAmountVoucher(voucherId, 100L, LocalDateTime.now()));

        given(voucherService.findVouchers(any(VoucherFindRequest.class))).willReturn(vouchers);

        // when
        ResultActions resultActions = mockMVC.perform(get("/api/v1/vouchers")
            .param("type", "FIXED_AMOUNT")
            .param("start", "202201010101")
            .param("end", "202207101010")
            .accept(MediaType.APPLICATION_JSON)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        VoucherDto[] voucherDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            VoucherDto[].class);
        assertThat(voucherDtos).hasSize(1)
            .extracting("voucherId")
            .containsExactly(voucherId);
    }

    @DisplayName("생성기간과 할인타입이 하나라도 알맞은 형식이 아닐 경우 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"FIXED,202201011010,202201011010", "FIXED_AMOUNT,2022,202201011010",
        "FIXED_AMOUNT,202201011010,"})
    void getVoucherWithCreatedAndType_WrongRequest_ThrowsException(String type, String start, String end) throws
        Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", type);
        params.add("start", start);
        params.add("end", end);

        // when
        ResultActions resultActions = mockMVC.perform(get("/api/v1/vouchers")
            .params(params)
            .accept(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().is4xxClientError())
            .andExpect(jsonPath("message", "알맞은 형식이 아닙니다.").exists());

    }

    @DisplayName("ID를 통해 바우처를 삭제한다.")
    @Test
    void deleteVoucher() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();

        // when
        ResultActions resultActions = mockMVC.perform(delete("/api/v1/vouchers/{voucherId}", voucherId.toString())
            .accept(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isOk());
        then(voucherService).should(times(1)).delete(any(UUID.class));
    }

    private Voucher voucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now());
    }

    private List<Voucher> vouchers() {
        return List.of(new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 100L, LocalDateTime.now()),
            new PercentDiscountVoucher(UUID.randomUUID(), 100L, LocalDateTime.now()));
    }

    private VoucherRequest voucherRequest() {
        return new VoucherRequest(1, 100L);
    }

}
