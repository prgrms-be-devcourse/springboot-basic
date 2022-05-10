package com.example.voucher.api;

import com.example.voucher.VoucherApplication;
import com.example.voucher.config.ServiceConfiguration;
import com.example.voucher.controller.api.CustomerRestController;
import com.example.voucher.domain.customer.Customer;
import com.example.voucher.dto.*;
import com.example.voucher.service.customer.CustomerService;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, CustomerRestController.class})
@WebMvcTest(controllers = CustomerRestController.class,
		excludeAutoConfiguration = VoucherApplication.class)
@DisplayName("CustomerRestController 클래스의")

public class CustomerRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoucherApplication voucherApplication;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerService customerService;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		@Test
		@DisplayName("전체 고객을 조회하고 반환한다")
		void 전체_고객을_조회하고_반환한다() throws Exception {
			List<Customer> customers = Arrays.asList(new Customer(1L, "testName", LocalDateTime.now(), null));
			List<CustomerResponse> customerResponses = customers.stream()
					.map((v) -> CustomerResponse.from(v))
					.collect(Collectors.toList());
			String responseJsonString = objectToJsonString(customerResponses);
			given(customerService.findAll())
					.willReturn(customers);

			mockMvc.perform(get("/api/v1/customers"))
					.andExpect(status().isOk())
					.andExpect(content().json(responseJsonString));
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_고객이_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() throws Exception {

				String responseJsonString = objectToJsonString(Collections.EMPTY_LIST);
				given(customerService.findAll())
						.willReturn(Collections.EMPTY_LIST);


				mockMvc.perform(get("/api/v1/customers"))
						.andExpect(status().isOk())
						.andExpect(content().json(responseJsonString));
			}
		}
	}

	@Nested
	@Order(1)
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("고객을 저장하고 저장된 고객를 반환한다")
		void 고객을_저장하고_저장된_고객을_반환한다() throws Exception {

			CustomerRequest customerRequest = new CustomerRequest("testName");
			String requestJsonString = objectToJsonString(customerRequest);
			Customer customer = new Customer(1L, customerRequest.getName(), LocalDateTime.now(), null);
			CustomerResponse customerResponse = new CustomerResponse(customer.getCustomerId(), customer.getName());
			String responseJsonString = objectToJsonString(customerResponse);
			given(customerService.save(anyString()))
					.willReturn(customer);

			mockMvc.perform(post("/api/v1/customers")
					.content(requestJsonString)
					.contentType(APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json(responseJsonString));
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 이름이_null이라면 {

			@Test
			@DisplayName("예외 응답을 반환한다")
			void 예외_응답을_반환한다() throws Exception {

				CustomerRequest customerRequest = new CustomerRequest(null);
				String requestJsonString = objectToJsonString(customerRequest);

				MvcResult mvcResult = mockMvc.perform(post("/api/v1/customers")
						.content(requestJsonString)
						.contentType(APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andReturn();

				assertThat(mvcResult.getResolvedException().getMessage()).contains("이름은 필수 입력입니다.");
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class deleteById메서드는 {

		@Test
		@DisplayName("고객 아이디로 고객을 삭제한다")
		void 고객_아이디로_고객을_삭제한다() throws Exception {
			Long customerId = 1L;
			doNothing().when(customerService).deleteById(anyLong());


			mockMvc.perform(delete("/api/v1/customers/" + customerId))
							.andExpect(status().isOk());
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 고객_아이디가_null이라면 {

			@Test
			@DisplayName("예외 응답을 반환한다")
			void 예외_응답을_반환한다() throws Exception {

				mockMvc.perform(delete("/api/v1/customers/null"))
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
}