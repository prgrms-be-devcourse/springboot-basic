package com.example.voucher.service.customer;

import com.example.voucher.domain.customer.Customer;
import com.example.voucher.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService 클래스의")
public class CustomerServiceTest {
	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		@Test
		@DisplayName("고객을 전체 조회하고 반환한다")
		void 고객을_전체_조회하고_반환한다() {
			// given
			List<Customer> createdCustomers = Arrays.asList(
					new Customer(1L, "testName1", LocalDateTime.now(), null),
					new Customer(2L, "testName2", LocalDateTime.now(), null));
			given(customerRepository.findAll())
					.willReturn(createdCustomers);

			// when
			List<Customer> customers = customerService.findAll();

			// then
			verify(customerRepository).findAll();
			assertThat(customers).isEqualTo(createdCustomers);
		}
	}
	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("커스터머를 저장한다")
		void 커스터머를_저장한다() {
			// given
			Customer createdCustomer = new Customer(1L, "testName", LocalDateTime.now(), null);
			given(customerRepository.save(any(Customer.class)))
					.willReturn(createdCustomer);
			// when
			Customer customer = customerService.save("testName");

			// then
			verify(customerRepository).save(any(Customer.class));
			assertThat(customer).isEqualTo(createdCustomer);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 이름이_널이거나_공백이거나_빈_값이라면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> customerService.save(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.getMessage());

				assertThatThrownBy(() -> customerService.save(""))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.getMessage());

				assertThatThrownBy(() -> customerService.save(" "))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.getMessage());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class deleteById메서드는 {

		@Test
		@DisplayName("고객 아이디로 고객을 삭제한다")
		void 고객_아이디로_고객을_삭제한다() {
			customerService.deleteById(1L);

			verify(customerRepository).deleteById(anyLong());
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 고객_아이디가_null이라면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> customerService.deleteById(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.getMessage());
			}
		}
	}
}
