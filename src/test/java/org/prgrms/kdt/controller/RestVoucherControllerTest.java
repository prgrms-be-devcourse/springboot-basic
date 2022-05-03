package org.prgrms.kdt.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.dto.VoucherRequest;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.type.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RestVoucherController.class)
class RestVoucherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private VoucherService voucherService;

  @Test
  @DisplayName("모든 바우처를 조회한다.")
  void find_all_vouchers() throws Exception {
    var mvcResult = mockMvc.perform(get("/api/v1/vouchers")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
  }

  @Test
  @DisplayName("ID에 해당하는 바우처를 조회한다.")
  void find_voucher_by_id() throws Exception {
    var voucherDto = new VoucherDto(UUID.randomUUID(), UUID.randomUUID(), 100L, VoucherType.of(1),
        LocalDateTime.now());
    var voucher = new FixedAmountVoucher(voucherDto);
    given(voucherService.findById(voucher.getVoucherId())).willReturn(voucher);

    mockMvc.perform(get("/api/v1/vouchers/{voucherId}", voucher.getVoucherId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("voucherId").value(voucher.getVoucherId().toString()))
        .andExpect(jsonPath("customerId").value(voucher.getCustomerId().toString()))
        .andExpect(jsonPath("amount").value(voucher.getAmount()))
        .andExpect(jsonPath("type").value(voucher.getType().toString()));
  }

  @Test
  @DisplayName("바우처를 생성한다.")
  void create_voucher() throws Exception {
    var createRequest = new VoucherRequest.CreateRequest(1, 100L);
    var voucherDto = VoucherDto.of(createRequest);
    var voucher = voucherDto.voucherType().create(voucherDto);
    given(voucherService.create(voucherDto)).willReturn(voucher);

    mockMvc.perform(post("/api/v1/vouchers")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("바우처를 삭제한다.")
  void delete_voucher() throws Exception {
    var voucherId = UUID.randomUUID();

    mockMvc.perform(delete("/api/v1/vouchers/{voucherId}", voucherId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("바우처를 할당한다.")
  void assign_voucher() throws Exception {
    var voucherId = UUID.randomUUID();
    var customerId = UUID.randomUUID();
    var voucherDto = new VoucherDto(voucherId, customerId, 100L,
        VoucherType.of(1), LocalDateTime.now());
    var voucher = new FixedAmountVoucher(voucherDto);
    given(voucherService.assign(voucherId, customerId)).willReturn(voucher);

    var assignRequest = new VoucherRequest.AssignRequest(customerId);

    mockMvc.perform(patch("/api/v1/vouchers/{voucherId}", voucherId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(assignRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("customerId").value(customerId.toString()));
  }
}