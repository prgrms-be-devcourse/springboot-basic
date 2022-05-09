package org.programmers.devcourse.voucher.engine.voucher.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.programmers.devcourse.voucher.engine.voucher.VoucherTestUtil.voucherFixtures;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {VoucherRestController.class})
class VoucherRestControllerTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private VoucherService voucherService;

  @Test
  @DisplayName("모든 바우처들을 json으로 불러온다.")
  void getAllVouchers() throws Exception {
    // Given
    when(voucherService.getAllVouchers()).thenReturn(voucherFixtures);
    var voucherJson = objectMapper.writeValueAsString(
        voucherFixtures.stream().map(VoucherDto::from).collect(Collectors.toList()));

    // When
    mockMvc.perform(get("/api/v1/vouchers")).andExpectAll(
        // Then
        MockMvcResultMatchers.content().json(voucherJson), status().is2xxSuccessful()
    );
  }

  @Test
  void getAllVouchersByType() throws Exception {
    // Given
    var typeId = VoucherType.FIXED_AMOUNT.getTypeId();
    var fixedAmountVouchers = voucherFixtures.stream().filter(
            voucher -> VoucherType.mapToTypeId(voucher).equals(typeId))
        .collect(
            Collectors.toList());
    var expectedVoucherDtoJson = objectMapper.writeValueAsString(
        fixedAmountVouchers.stream().map(VoucherDto::from)
            .collect(Collectors.toUnmodifiableList()));

    // When
    when(voucherService.getVouchersByType(typeId)).thenReturn(fixedAmountVouchers);
    mockMvc.perform(get("/api/v1/vouchers/type/" + typeId))
        //then
        .andExpectAll(content().json(expectedVoucherDtoJson));

  }

  @Test
  @DisplayName("날짜 문자열을 정확히 파싱해서 서비스에 전달하고, 바우처들을 response에 담아 응답하는가.")
  void getAllVouchersByCreatedAt() throws Exception {
    // Given
    var testDate = LocalDate.now().minusDays(2).atStartOfDay();
    var testDateString = testDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    var expectedVoucherJson = objectMapper.writeValueAsString(
        voucherFixtures.stream().map(VoucherDto::from)
            .collect(Collectors.toUnmodifiableList()));

    // When
    when(voucherService.getVouchersByCreatedAt(any(LocalDateTime.class))).thenReturn(
        voucherFixtures);
    var requestResult = mockMvc.perform(get("/api/v1/vouchers/created-at/" + testDateString));

    // Then
    requestResult.andExpect(content().json(expectedVoucherJson));
    var dateArgumentCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
    verify(voucherService, times(1)).getVouchersByCreatedAt(dateArgumentCaptor.capture());
    assertThat(dateArgumentCaptor.getValue()).isEqualTo(testDate);
  }

  @Test
  @DisplayName("URL로 잘못된 date 값이 올 경우, 400 상태 코드와 에러 메시지가 담긴 json을 응답해야 한다.")
  void send_400_status_code_and_error_message_when_wrong_date_url_input() throws Exception {

    // Given
    var wrongDateString = "20ff3412444";
    // When
    var requestResult = mockMvc.perform(get("/api/v1/vouchers/created-at/" + wrongDateString));
    // Then
    requestResult.andExpectAll(
        status().is4xxClientError(),
        jsonPath("$.message").value("날짜 입력이 잘못되었습니다.")
    );
  }

  @Test
  void getVoucherById() throws Exception {
    // Given
    var voucher = voucherFixtures.get(0);
    var id = voucher.getVoucherId();
    var expectedJson = objectMapper.writeValueAsString(VoucherDto.from(voucher));
    // When
    when(voucherService.getVoucherById(id)).thenReturn(voucher);
    var requestResult = mockMvc.perform(get("/api/v1/vouchers/id/" + id));
    // Then
    requestResult.andExpect(content().json(expectedJson));
  }

  @Test
  void registerVoucher() throws Exception {
    // Given
    var voucher = voucherFixtures.get(0);
    var voucherType = VoucherType.mapToTypeId(voucher);
    var discountDegree = voucher.getDiscountDegree();
    var requestJson = objectMapper.writeValueAsString(
        new VoucherRegistrationDto(voucherType, discountDegree));
    var responseJson = objectMapper.writeValueAsString(VoucherDto.from(voucher));

    // When
    when(voucherService.create(voucherType, discountDegree)).thenReturn(voucher);
    var requestResult = mockMvc.perform(
        post("/api/v1/vouchers")
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON));

    // Then
    requestResult.andExpect(content().json(responseJson));
  }

  @Test
  @DisplayName("URL에서 UUID를 제대로 파싱하여 서비스의 삭제 메서드에 위임해야 한다.")
  void deleteVoucher() throws Exception {
    // Given
    var voucher = voucherFixtures.get(0);
    var id = voucher.getVoucherId();
    var UUIDCaptor = ArgumentCaptor.forClass(UUID.class);

    // When
    var requestResult = mockMvc.perform(delete("/api/v1/vouchers/" + id.toString()));

    // Then
    requestResult.andExpect(jsonPath("$.id").value(id.toString()));
    verify(voucherService, times(1)).remove(UUIDCaptor.capture());
    assertThat(UUIDCaptor.getValue()).isEqualTo(id);
  }
}
