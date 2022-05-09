package org.programmers.devcourse.voucher.engine.voucher.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.programmers.devcourse.voucher.engine.voucher.VoucherTestUtil.voucherFixtures;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.devcourse.voucher.engine.customer.CustomerRepository;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest()
class WebPageVoucherControllerTest {

  @Autowired
  private MockMvc mockMvc;
  // TODO : PR Point 2
  @MockBean
  private VoucherService voucherService;
  @MockBean
  private CustomerRepository customerRepository;
  @MockBean
  private JdbcVoucherRepository voucherRepository;

  @Test
  void show_all_vouchers() throws Exception {
    when(voucherService.getAllVouchers()).thenReturn(voucherFixtures);
    mockMvc.perform(get("/voucher")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Voucher의 id값을 입력했을 때 제대로 된 Voucher 정보를 반환하는 페이지를 생성해야 한다.")
  void show_voucher_by_id() throws Exception {
    var voucher = voucherFixtures.get(0);
    when(voucherService.getVoucherById(voucher.getVoucherId())).thenReturn(voucher);
    // TODO : PR Point 1
    mockMvc.perform(get("/voucher/" + voucher.getVoucherId())).andDo(
        result -> {
          var modelAndView = result.getModelAndView();
          assertThat(modelAndView).isNotNull();
          assertThat(modelAndView.getViewName()).isEqualTo("voucher.html");
          var voucherModel = modelAndView.getModel().get("voucher");
          assertThat(voucherModel).isInstanceOf(VoucherDto.class);
          assertThat(((VoucherDto) voucherModel).getVoucherId()).isEqualTo(voucher.getVoucherId());
        }).andExpect(status().isOk());
  }

  @Test
  void show_voucher_registration_form() throws Exception {
    mockMvc.perform(get("/voucher/register"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("new-vouchers.html"));
  }

  @Test
  @DisplayName("등록 폼 형식이 일치할 경우 등록 요청을 마치고 redirect 응답을 보 내야 한다.")
  void register_voucher_with_correct_parameter() throws Exception {
    VoucherRegistrationDto dto = new VoucherRegistrationDto(
        String.valueOf(VoucherType.FIXED_AMOUNT.ordinal() + 1), 10000);
    mockMvc.perform(
            post("/voucher/register").param("voucherType", "1").param("discountDegree", "10000"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/voucher"));
  }

  @ParameterizedTest
  @CsvSource({"voucherTypesf,4444,discountDegree,10000", "voucherType,2424,discountDegreeff,10000"})
  @DisplayName("등록폼 형식이 일치하지 않을 경우 400 응답과, 오류 웹 페이지로 보내야 한다.")
  void register_voucher_with_wrong_parameter(String voucherType, String voucherTypeValue,
      String discountDegree, String discountDegreeValue) throws Exception {
    mockMvc.perform(post("/voucher/register")
            .param(voucherType, voucherTypeValue)
            .param(discountDegree, discountDegreeValue))
        .andExpect(status().is4xxClientError()).andExpect(view().name("web-error.html"))
        .andExpect(content().string(Matchers.containsString(HttpStatus.BAD_REQUEST.toString())));
  }

  @Test
  @DisplayName("삭제 요청을 한 ID의 형식이 유효하면 바우처 삭제를 마치고 메인 페이지로 redirect한다.")
  void delete_voucher_with_proper_UUID() throws Exception {
    mockMvc.perform(post("/voucher/delete").param("voucherId", UUID.randomUUID().toString()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/voucher"));
  }

  @ParameterizedTest
  @CsvSource({"voucherId,sdffasdfxcvf", "wrongparamname,2a8b9a0c-1fa7-4349-af90-5e5e6a03fd17"})
  @DisplayName("삭제 요청을 한 ID의 형식이 유효하지 않으면 client error 응답을 보내야 한다.")
  void delete_voucher_with_wrong_param_should_send_4xx_client_error(String voucherIdKey,
      String voucherIdLiteral) throws Exception {
    mockMvc.perform(post("/voucher/delete").param(voucherIdKey, voucherIdLiteral))
        .andExpect(status().is4xxClientError());
  }

}
