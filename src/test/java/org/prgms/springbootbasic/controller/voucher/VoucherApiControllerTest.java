package org.prgms.springbootbasic.controller.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherRequestDto;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class VoucherApiControllerTest { // 컨트롤러 유닛 테스트로. (죄다 나머지 목 객체)
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private VoucherService voucherService;
    private final String uri = "/api/v1/vouchers";
    private UUID setUpVoucherId;
    private Voucher setUpVoucher;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        setUpVoucher = voucherService.insert(VoucherType.FIXED_AMOUNT, 1000);
        this.setUpVoucherId = setUpVoucher.getVoucherId();
    }

    @AfterEach
    void clean() {
        this.voucherService.deleteAll();
    }// 예외는 컨트롤러 어드바이스에서 함께 처리할 수 있음. REST 공부하기 깊은 이해로. MVC 의존관계. DTO 관련 어디에 둬야 할지. DTO가 뭔지 깊은 이해.
// 단위 테스트만의 장점: fixed 들어왔을때 에러 뱉어야해 테스트 혹은 데이터 설정, 통합은 느림.
// 단위는 빠름. 원하는 결과를 해당 부분만 독립적으로 테스트가 가능. 검증 쉽고.
    // 다음 플젝에서는 이를 전부 반영하자.
    // 이 과제 피드백도 반영 후 이유를 적어 올리자.
    @Test // 디스플레이네임
    void getVouchersFilteredByQueryParameter() throws Exception {
        this.mockMvc.perform(get(uri).param("policy", "FixedAmountPolicy")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].voucherId").value(setUpVoucherId.toString()));
    }

    @Test
    void showVoucherInDetailsFormattedByJSON() throws Exception {
        this.mockMvc.perform(get(uri + "/{id}", setUpVoucherId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").value(setUpVoucherId.toString()))
                .andExpect(jsonPath("$.discountDegree").value(1000))
                .andExpect(jsonPath("$.voucherPolicy").value("FixedAmountPolicy"));
    }

    @Test
    void createVoucher() throws Exception {
        VoucherRequestDto voucherRequestDto = new VoucherRequestDto(UUID.randomUUID().toString(),
                VoucherType.PERCENT_DISCOUNT.getDisplayName(),
                20);

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voucherRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discountDegree").value(20))
                .andExpect(jsonPath("$.voucherPolicy").value(VoucherType.PERCENT_DISCOUNT.getDisplayName()));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(uri + "/{id}", setUpVoucherId))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertTrue(voucherService.findAll().isEmpty());
    }
}
