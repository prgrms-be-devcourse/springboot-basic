package org.promgrammers.springbootbasic.domain.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class VoucherApiControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JdbcVoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처 생성 성공")
    void createVoucherSuccessTest() throws Exception {

        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(VoucherType.PERCENT, 10);

        mockMvc.perform(post("/api/vouchers/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createVoucherRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 생성 실패 - 잘못된 금액")
    void createVoucherFailTest_Amount() throws Exception {

        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(VoucherType.PERCENT, 1200);

        mockMvc.perform(post("/api/vouchers/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createVoucherRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 조회")
    void findAllSuccessTest() throws Exception {

        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));

        mockMvc.perform(get("/api/vouchers/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 조회 실패 - 바우처 존재하지 않음")
    void findAllFailTest() throws Exception {

        mockMvc.perform(get("/api/vouchers/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @DisplayName("바우처 타입으로 조회 성공")
    void findByTypeSuccessTest() throws Exception {

        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));
        VoucherType voucherType = voucher.getVoucherType();

        mockMvc.perform(get("/api/vouchers").param("voucherType", voucherType.getValue())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("조회 성공"))
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 타입으로 조회 실패 - 저장되지 않은 타입의 바우처")
    void findByTypeFailTest() throws Exception {

        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));
        VoucherType voucherType = voucher.getVoucherType();

        mockMvc.perform(get("/api/vouchers").param("voucherType", "percent")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 ID로 조회 성공")
    void findByIdSuccessTest() throws Exception {

        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));

        mockMvc.perform(get("/api/vouchers/{id}", voucher.getVoucherId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.voucherId").value(voucher.getVoucherId().toString()))
                .andExpect(jsonPath("$.body.voucherType").value(voucher.getVoucherType().getValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 ID로 조회 실패 - 존재하지 않는 ID")
    void findByIdFailTest() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/api/vouchers/{id}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 아이디로 단건 삭제")
    void deleteByIdSuccessTest() throws Exception {

        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));

        mockMvc.perform(delete("/api/vouchers/{id}", voucher.getVoucherId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("삭제 완료"))
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 ID로 단건 삭제 실패 - 존재하지 않는 ID")
    void deleteByIdFailTest() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/vouchers/{id}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 삭제 성공")
    void deleteAllSuccessTest() throws Exception {

        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10));

        mockMvc.perform(delete("/api/vouchers"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 삭제 실패 - 존재하는 바우처 없음")
    void deleteAllFailTest() throws Exception {

        mockMvc.perform(delete("/api/vouchers"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}