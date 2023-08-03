package org.prgrms.kdt.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.domain.Voucher;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VoucherAPIController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherAPIControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoucherService voucherService;

	@Autowired
	private ObjectMapper objectMapper;
	private VoucherRequest voucherRequest;
	private List<VoucherResponse> voucherResponses;

	@BeforeAll
	void setup() {
		voucherResponses = new ArrayList<>();

		voucherRequest = new VoucherRequest(1, "FixedAmountVoucher");

		VoucherResponse voucherResponse = new VoucherResponse(1L, 90, "FixedAmountVoucher");
		voucherResponses.add(voucherResponse);

		Voucher newVoucher = voucherRequest.toDto();
	}

	@Test
	@DisplayName("api로 바우처를 불러올 수 있다.")
	void findVouchersTest() throws Exception {
		// given
		given(voucherService.getVouchers()).willReturn(voucherResponses);

		// when
		ResultActions resultActions = this.mockMvc.perform(
			MockMvcRequestBuilders.get("/api/vouchers")
		);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(voucherResponses)));
	}

	@Test
	@DisplayName("api로 바우처를 찾을 수 있다.")
	void 바우처_찾기_테스트() throws Exception {
		// given
		VoucherResponse expectedVoucherResponse = voucherResponses.get(0);
		given(voucherService.findVoucherById(expectedVoucherResponse.voucherId())).willReturn(expectedVoucherResponse);

		// when
		ResultActions resultActions = this.mockMvc.perform(
			MockMvcRequestBuilders.get(MessageFormat.format("/api/vouchers/{0}", expectedVoucherResponse.voucherId()))
		);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(expectedVoucherResponse)));
	}

	@Test
	@DisplayName("api로 바우처를 삭제할 수 있다.")
	void 바우처_삭제_테스트() throws Exception {
		// when
		ResultActions resultActions = this.mockMvc.perform(
			MockMvcRequestBuilders
				.delete(MessageFormat.format("/api/vouchers/{0}",1))
		);

		// then
		resultActions
			.andExpect(status().isOk());
	}
	@Test
	@DisplayName("api로 바우처를 저장할 수 있다.")
	void 바우처_저장_테스트() throws Exception {
		// given
		String content = objectMapper.writeValueAsString(voucherRequest);

		// when
		ResultActions resultActions = this.mockMvc.perform(
			MockMvcRequestBuilders
				.post("/api/vouchers/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(content)
		);

		// then
		resultActions
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("api로 voucherType을 검색할 수 있다.")
	void 바우처타입_검색_테스트() throws Exception {
		// given
		VoucherType targetVoucherType = VoucherType.FixedAmountVoucher;
		List<VoucherResponse> filteredVoucherResponses = voucherResponses.stream()
			.filter(
				voucherResponse ->
					voucherResponse.voucherType().equals(targetVoucherType.toString())
			).collect(Collectors.toList());

		given(voucherService.findVoucherByVoucherType(targetVoucherType))
			.willReturn(filteredVoucherResponses);

		String content = objectMapper.writeValueAsString(filteredVoucherResponses);

		// when
		ResultActions resultActions = this.mockMvc.perform(
			MockMvcRequestBuilders
				.get("/api/vouchers/search")
				.param("voucher-type",targetVoucherType.toString())
		);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().json(content));
	}
}