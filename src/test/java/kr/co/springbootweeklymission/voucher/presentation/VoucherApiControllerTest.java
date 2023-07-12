package kr.co.springbootweeklymission.voucher.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.springbootweeklymission.global.error.exception.NotFoundException;
import kr.co.springbootweeklymission.global.response.ResponseStatus;
import kr.co.springbootweeklymission.voucher.application.VoucherService;
import kr.co.springbootweeklymission.voucher.creators.VoucherCreators;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;
import kr.co.springbootweeklymission.voucher.presentation.dto.response.VoucherResDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherApiController.class)
class VoucherApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoucherService voucherService;

    @Test
    void createVoucher_바우처를_생성_SUCCESS() throws Exception {
        //given
        VoucherReqDTO.CREATE create = VoucherCreators.createFixedDiscountDto();
        ResponseStatus expected = ResponseStatus.SUCCESS_CREATE_VOUCHER;

        //when
        doNothing().when(voucherService).createVoucher(any());

        //then
        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

    @Test
    void getVoucherById_특정_바우처를_조회_SUCCESS() throws Exception {
        //given
        UUID id = UUID.randomUUID();
        VoucherResDTO.READ read = VoucherCreators.readFixedDiscountDto(id);
        given(voucherService.getVoucherById(any())).willReturn(read);

        //when & then
        mockMvc.perform(get("/api/v1/vouchers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(read)));
    }

    @Test
    void getVoucherById_특정_바우처를_조회_NotFoundException() throws Exception {
        //given
        given(voucherService.getVoucherById(any())).willThrow(NotFoundException.class);

        //when & then
        mockMvc.perform(get("/api/v1/vouchers/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getVouchersAll_모든_바우처를_조회_SUCCESS() throws Exception {
        //given
        List<VoucherResDTO.READ> reads = new ArrayList<>();
        reads.add(VoucherCreators.readFixedDiscountDto());
        reads.add(VoucherCreators.readFixedDiscountDto());
        given(voucherService.getVouchersAll()).willReturn(reads);

        //when & then
        mockMvc.perform(get("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(reads)));
    }

    @Test
    void updateVoucherById_특정_바우처를_수정_SUCCESS() throws Exception {
        //given
        VoucherReqDTO.UPDATE update = VoucherCreators.updateVoucherInformation(10, VoucherPolicy.PERCENT_DISCOUNT);
        ResponseStatus expected = ResponseStatus.SUCCESS_UPDATE_VOUCHER;

        //when
        doNothing().when(voucherService).updateVoucherById(any(), any());

        //then
        mockMvc.perform(put("/api/v1/vouchers/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }

    @Test
    void deleteVoucherById_특정_바우처를_삭제_SUCCESS() throws Exception {
        //given
        ResponseStatus expected = ResponseStatus.SUCCESS_DELETE_VOUCHER;

        //when
        doNothing().when(voucherService).deleteVoucherById(any());

        //then
        mockMvc.perform(delete("/api/v1/vouchers/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(expected.getHttpStatus().value()))
                .andExpect(content().string(expected.getMessage()));
    }
}
