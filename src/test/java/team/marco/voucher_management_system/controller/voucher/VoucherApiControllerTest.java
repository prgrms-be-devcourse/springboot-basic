package team.marco.voucher_management_system.controller.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherCreateRequest;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;

@WebMvcTest(controllers = VoucherApiController.class)
class VoucherApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoucherService voucherService;

    @DisplayName("전체 쿠폰 목록을 조회할 수 있다.")
    @Test
    void findAllVouchers() throws Exception {
        // given
        List<Voucher> vouchers = new ArrayList<>();

        when(voucherService.getVouchers()).thenReturn(vouchers);

        // when then
        mockMvc.perform(
                get("/api/vouchers")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("쿠폰 유형을 넘기면 해당 유형의 쿠폰 목록을 조회할 수 있다.")
    @Test
    void findAllVouchersWithVoucherType() throws Exception {
        // given
        VoucherType voucherType = FIXED;
        List<Voucher> vouchers = new ArrayList<>();

        when(voucherService.getVouchers()).thenReturn(vouchers);

        // when then
        mockMvc.perform(
                        get("/api/vouchers")
                                .param("voucherType", voucherType.name())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("쿠폰 ID로 쿠폰 상세 정보를 조회할 수 있다.")
    @Test
    void getVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher.Builder(1L, FIXED, 1_000).build();

        when(voucherService.getVoucher(voucher.getId())).thenReturn(voucher);

        // when then
        mockMvc.perform(
                get("/api/vouchers/{voucherId}", 1L)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data.id").value(voucher.getId()))
            .andExpect(jsonPath("$.data.code").value(voucher.getCode().toString()))
            .andExpect(jsonPath("$.data.name").value(voucher.getName()));
    }

    @DisplayName("쿠폰을 생성할 수 있다.")
    @Test
    void createVoucher() throws Exception {
        // given
        VoucherCreateRequest request = new VoucherCreateRequest(FIXED, 1000);
        Voucher voucher = new Voucher.Builder(1L, FIXED, 1000).build();
        when(voucherService.createVoucher(any())).thenReturn(voucher);

        // when then
        mockMvc.perform(
                post("/api/vouchers")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @DisplayName("쿠폰 ID로 쿠폰을 삭제할 수 있다.")
    @Test
    void deleteVoucher() throws Exception {
        // given
        Long voucherId = 1L;

        // when
        mockMvc.perform(
                delete("/api/vouchers/{voucherId}", voucherId)
        ).andExpect(status().isOk()).andReturn();
    }
}