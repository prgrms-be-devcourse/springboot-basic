package com.example.voucher.api;

import com.example.voucher.VoucherApplication;
import com.example.voucher.config.ServiceConfiguration;
import com.example.voucher.controller.api.VoucherRestController;
import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.dto.VoucherRequest;
import com.example.voucher.dto.VoucherResponse;
import com.example.voucher.service.voucher.VoucherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {VoucherRestController.class, ServiceConfiguration.class})
@WebMvcTest(controllers = VoucherRestController.class,
	excludeAutoConfiguration = VoucherApplication.class)
@DisplayName("VoucherRestController 클래스의")
public class VoucherRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoucherApplication voucherApplication;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private VoucherService voucherService;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 검색_조건이_없다면 {

			@Test
			@DisplayName("저장된 바우처를 전체 조회하고 반환한다")
			void 저장된_바우처를_전체_조회하고_반환한다() throws Exception {

				List<Voucher> vouchers = Arrays.asList(new FixedAmountVoucher(1L, 10000),
						new FixedAmountVoucher(2L, 2000));
				List<VoucherResponse> voucherResponses = vouchers.stream()
						.map((v) -> VoucherResponse.from(v))
						.collect(Collectors.toList());
				String responseJsonString = objectToJsonString(voucherResponses);
				given(voucherService.findAll())
						.willReturn(vouchers);

				MvcResult mvcResult = mockMvc.perform(get("/api/v1/vouchers"))
						.andReturn();

				assertThat(getStatus(mvcResult)).isEqualTo(OK.value());
				assertThat(getResponseString(mvcResult)).isEqualTo(responseJsonString);
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 생성기간이_주어진다면 {

			@Test
			@DisplayName("생성기간으로 바우처를 조회하고 반환한다")
			void 생성기간으로_바우처를_조회하고_반환한다() throws Exception {
				List<Voucher> vouchers = Arrays.asList(new FixedAmountVoucher(1L, 10000),
						new FixedAmountVoucher(2L, 2000));
				List<VoucherResponse> voucherResponses = vouchers.stream()
						.map((v) -> VoucherResponse.from(v))
						.collect(Collectors.toList());
				String responseJsonString = objectToJsonString(voucherResponses);
				given(voucherService.findByCreatedAt(any(LocalDate.class)))
						.willReturn(vouchers);

				mockMvc.perform(get("/api/v1/vouchers?createdAt="+ LocalDate.now()))
									.andExpect(status().isOk())
									.andExpect(content().json(responseJsonString));
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입이_주어진다면 {

			@Test
			@DisplayName("바우터_타입으로_바우처를_조회하고 반환한다")
			void 바우터_타입으로_바우처를_조회하고_반환한다() throws Exception {

				List<Voucher> vouchers = Arrays.asList(new FixedAmountVoucher(1L, 10000),
						new FixedAmountVoucher(2L, 2000));
				List<VoucherResponse> voucherResponses = vouchers.stream()
						.map((v) -> VoucherResponse.from(v))
						.collect(Collectors.toList());
				String responseJsonString = objectToJsonString(voucherResponses);
				given(voucherService.findByVoucherType(any(VoucherType.class)))
						.willReturn(vouchers);

				mockMvc.perform(get("/api/v1/vouchers?voucherType="+FIXED_AMOUNT_VOUCHER))
								.andExpect(status().isOk())
								.andExpect(content().json(responseJsonString));
			}
		}
		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_바우처가_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() throws Exception {

				String requestJsonString = objectToJsonString(FIXED_AMOUNT_VOUCHER);
				String responseJsonString = objectToJsonString(Collections.EMPTY_LIST);
				given(voucherService.findAll())
						.willReturn(Collections.EMPTY_LIST);

				mockMvc.perform(get("/api/v1/vouchers")
								.content(requestJsonString)
								.contentType(APPLICATION_JSON))
								.andExpect(status().isOk())
								.andExpect(content().json(responseJsonString));
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("바우처를 저장하고 저장된 바우처를 반환한다")
		void 바우처를_저장하고_저장된_바우처를_반환한다() throws Exception {

			Voucher createdVoucher = new FixedAmountVoucher(1L, 1000);
			String requestJsonString = objectToJsonString(new VoucherRequest(createdVoucher.getVoucherType(), createdVoucher.getDiscountAmount()));
			String responseJsonString = objectToJsonString(new VoucherResponse(createdVoucher.getVoucherId(), createdVoucher.getDiscountAmount(), createdVoucher.getCreatedAt()));
			given(voucherService.save(any(VoucherType.class), anyInt()))
					.willReturn(createdVoucher);

			MvcResult mvcResult = mockMvc.perform(post("/api/v1/vouchers")
							.content(requestJsonString)
							.contentType(APPLICATION_JSON))
							.andReturn();

			assertThat(getStatus(mvcResult)).isEqualTo(OK.value());
			assertThat(getResponseString(mvcResult)).isEqualTo(responseJsonString);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입이_null이라면 {

			@Test
			@DisplayName("예외 응답을 반환한다")
			void 예외_응답을_반환한다() throws Exception {

				String requestJsonString = objectToJsonString(new VoucherRequest(null, 2000));


				MvcResult mvcResult = mockMvc.perform(post("/api/v1/vouchers")
								.content(requestJsonString)
								.contentType(APPLICATION_JSON))
								.andReturn();

				assertThat(getStatus(mvcResult)).isEqualTo(BAD_REQUEST.value());
				assertThat(mvcResult.getResolvedException().getMessage()).contains("바우처 타입이 null이 될 수 없습니다");
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class deleteById메서드는 {

		@Test
		@DisplayName("바우처 아이디로 바우처를 삭제한다")
		void 바우처_아이디로_바우처를_삭제한다() throws Exception {
			Long voucherId = 1L;
			doNothing().when(voucherService).deleteById(anyLong());


			mockMvc.perform(delete("/api/v1/vouchers/" + voucherId)
						.content(String.valueOf(voucherId))
						.contentType(APPLICATION_JSON))
						.andExpect(status().isOk());
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_아이디_null이라면 {

			@Test
			@DisplayName("예외 응답을 반환한다")
			void 예외_응답을_반환한다() throws Exception {

				mockMvc.perform(delete("/api/v1/vouchers/null"))
						.andExpect(status().isBadRequest());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findById메서드는 {

		@Test
		@DisplayName("바우처 아이디로 바우처를 조회한다")
		void 바우처_아이디로_바우처를_조회한다() throws Exception {

			Long voucherId = 1L;
			Voucher voucher = new FixedAmountVoucher(voucherId, 1000);
			String responseJsonString = objectToJsonString(VoucherResponse.from(voucher));
			given(voucherService.findById(anyLong()))
					.willReturn(voucher);

			mockMvc.perform(get("/api/v1/vouchers/"+voucherId))
					.andExpect(status().isOk())
					.andExpect(content().json(responseJsonString));
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_아이디_null이라면 {

			@Test
			@DisplayName("예외 응답을 반환한다")
			void 예외_응답을_반환한다() throws Exception {

				mockMvc.perform(get("/api/v1/vouchers/null"))
						 .andExpect(status().isBadRequest());

			}
		}
	}

	private String objectToJsonString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private String getResponseString(MvcResult mvcResult) {
		try {
			return mvcResult.getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private int getStatus(MvcResult mvcResult) {
		return mvcResult.getResponse().getStatus();
	}
}