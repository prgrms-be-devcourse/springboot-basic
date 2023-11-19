package org.prgms.springbootbasic.controller.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.service.dto.VoucherCreateRequestDto;
import org.prgms.springbootbasic.service.dto.VoucherInsertDto;
import org.prgms.springbootbasic.service.dto.VoucherResponseDto;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.service.VoucherService;
import org.prgms.springbootbasic.service.dto.VoucherFilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoucherApiController.class)
@ActiveProfiles("dev")
@Slf4j
class VoucherApiControllerTest { // 컨트롤러 유닛 테스트로. (죄다 나머지 목 객체)
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VoucherService voucherService;
    private final String uri = "/api/v1/vouchers";
    @Autowired
    private ObjectMapper objectMapper;

    // 예외는 컨트롤러 어드바이스에서 함께 처리할 수 있음. REST 공부하기 깊은 이해로. MVC 의존관계. DTO 관련 어디에 둬야 할지. DTO가 뭔지 깊은 이해.
// 단위 테스트만의 장점: fixed 들어왔을때 에러 뱉어야해 테스트 혹은 데이터 설정, 통합은 느림.
// 단위는 빠름. 원하는 결과를 해당 부분만 독립적으로 테스트가 가능. 검증 쉽고.
    // 다음 플젝에서는 이를 전부 반영하자.
    // 이 과제 피드백도 반영 후 이유를 적어 올리자.
    @Test
    @DisplayName("특정 기준으로 필터링된 바우처들을 조회할 수 있다.")
    void getVouchersFilteredByQueryParameter() throws Exception {
        LocalDateTime startDay = LocalDateTime.of(2022, 11, 1, 0, 0);
        LocalDateTime endDay = LocalDateTime.of(2023, 11, 1, 0, 0);

        VoucherFilterDto voucherFilterDto = new VoucherFilterDto(startDay, endDay, VoucherType.FIXED_AMOUNT);

        log.info(voucherFilterDto.toString());

        when(voucherService.findByPolicyBetweenLocalDateTime(eq(voucherFilterDto)))
                .thenReturn(List.of(new VoucherResponseDto(UUID.randomUUID(),
                                1000,
                                "FixedAmountPolicy",
                                LocalDateTime.of(2023, 6, 1, 11,10)),
                        new VoucherResponseDto(UUID.randomUUID(),
                                2000,
                                "FixedAmountPolicy",
                                LocalDateTime.of(2023, 8, 1, 18, 20))
                ));
        when(voucherService.convertToType("FixedAmountPolicy"))
                .thenReturn(VoucherType.FIXED_AMOUNT);

        this.mockMvc.perform(get(uri)
                        .param("startDay", startDay.toString())
                        .param("endDay", endDay.toString())
                        .param("voucherPolicy", "FixedAmountPolicy")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(voucherService).findByPolicyBetweenLocalDateTime(eq(voucherFilterDto));
    }

    @Test
    @DisplayName("JSON으로 포맷된 특정 바우처 세부 정보를 조회할 수 있다.")
    void showVoucherInDetailsFormattedByJSON() throws Exception {
        UUID setUpVoucherId = UUID.randomUUID();

        when(voucherService.findById(setUpVoucherId))
                .thenReturn(Optional.of(new VoucherResponseDto(setUpVoucherId,
                        1000,
                        "FixedAmountPolicy",
                        LocalDateTime.now())));

        this.mockMvc.perform(get(uri + "/{id}", setUpVoucherId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").value(setUpVoucherId.toString()))
                .andExpect(jsonPath("$.discountDegree").value(1000))
                .andExpect(jsonPath("$.voucherPolicy").value("FixedAmountPolicy"));

        verify(voucherService).findById(setUpVoucherId);
    }

    @Test
    @DisplayName("바우처 생성 로직을 실행할 수 있다.")
    void callCreateVoucher() throws Exception {
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto(VoucherType.PERCENT_DISCOUNT, 20);

        when(voucherService.insert(any(VoucherInsertDto.class)))
                .thenReturn(new VoucherResponseDto(UUID.randomUUID(),
                        20,
                        VoucherType.PERCENT_DISCOUNT.getDisplayName(),
                        LocalDateTime.now()));

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voucherCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discountDegree").value(20))
                .andExpect(jsonPath("$.voucherPolicy").value(VoucherType.PERCENT_DISCOUNT.getDisplayName()));

        verify(voucherService).insert(any(VoucherInsertDto.class));
    }

    @Test
    @DisplayName("특정 바우처를 삭제할 수 있다.")
    void callDeleteVoucher() throws Exception {
        UUID setUpVoucherId = UUID.randomUUID();

        mockMvc.perform(delete(uri + "/{id}", setUpVoucherId))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertTrue(voucherService.findAll().isEmpty());

        verify(voucherService).deleteById(setUpVoucherId);
    }
}
