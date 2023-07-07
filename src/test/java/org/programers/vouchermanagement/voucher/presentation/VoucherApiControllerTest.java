package org.programers.vouchermanagement.voucher.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.domain.VoucherType;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    void 바우처를_저장한다() throws Exception {
        // given
        VoucherCreationRequest request = new VoucherCreationRequest(VoucherType.PERCENT, 10);

        // when & then
        mockMvc.perform(post("/api/vouchers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 바우처를_아이디로_조회한다() throws Exception {
        // given
        UUID id =  UUID.randomUUID();

        // when & then
        mockMvc.perform(get("/api/vouchers/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 바우처를_타입으로_조회한다() throws Exception {
        // given
        VoucherType type = VoucherType.PERCENT;

        // when & then
        mockMvc.perform(get("/api/vouchers")
                        .param("type", type.name())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 바우처를_모두_조회한다() throws Exception {
        // given & when & then
        mockMvc.perform(get("/api/vouchers/all")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 바우처를_아이디로_삭제한다() throws Exception {
        // given
        UUID id =  UUID.randomUUID();

        // when & then
        mockMvc.perform(delete("/api/vouchers/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
