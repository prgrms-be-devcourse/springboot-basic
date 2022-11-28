package org.prgrms.springorder.controller.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.controller.dto.VoucherResponseDto;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("jdbc")
class VoucherRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	VoucherRepository voucherRepository;

	@BeforeEach()
	void clear() {
		voucherRepository.clear();
	}

	@Test
	@DisplayName("바우처를 성공적으로 저장한다.")
	void postTest() throws Exception {

		VoucherResponseDto voucherResponseDto = new VoucherResponseDto(
			UUID.randomUUID(),
			150,
			LocalDateTime.now(),
			VoucherType.FIXED_AMOUNT
		);

		String json = objectMapper.writeValueAsString(voucherResponseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/voucher")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

		Voucher voucher = voucherRepository.findAll().get(0);

		Assertions.assertThat(voucher.getValue()).isEqualTo(voucherResponseDto.getValue());
		Assertions.assertThat(voucher.getVoucherType()).isEqualTo(voucherResponseDto.getVoucherType());

	}

	@Test
	@DisplayName("바우처를 성공적으로 조회한다.")
	void getTest() throws Exception {

		double value = 150;

		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);

		voucherRepository.save(voucher);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/voucher/{voucherId}", voucher.getVoucherId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.voucherId").value(voucher.getVoucherId().toString()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.value").value(voucher.getValue()))
			.andDo(MockMvcResultHandlers.print());
	}
}