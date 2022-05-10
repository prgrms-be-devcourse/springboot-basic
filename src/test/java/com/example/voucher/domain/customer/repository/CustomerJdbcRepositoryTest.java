package com.example.voucher.domain.customer.repository;

import com.example.voucher.domain.customer.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = CustomerJdbcRepositoryTest.Config.class)
public class CustomerJdbcRepositoryTest {

	@Configuration
	@EnableAutoConfiguration
	static class Config {
		@Bean
		public CustomerJdbcRepository customerJdbcRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			return new CustomerJdbcRepository(dataSource, namedParameterJdbcTemplate);
		}
	}

	@Autowired
	private CustomerJdbcRepository customerJdbcRepository;

	@Nested
	@Order(1)
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("고객을 저장하고 저장된 고객을 반환한다")
		void 고객을_저장하고_저장된_고객을_반환한다() {
			// given
			Customer customer = new Customer("testName");

			// when
			Customer savedCustomer = customerJdbcRepository.save(customer);

			// then
			assertThat(savedCustomer.getName()).isEqualTo(savedCustomer.getName());
			assertThat(savedCustomer.getCreatedAt()).isEqualTo(savedCustomer.getCreatedAt());
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 고객이_null이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> customerJdbcRepository.save(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.getMessage());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_고객이_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				List<Customer> findVouchers = customerJdbcRepository.findAll();
				assertThat(findVouchers.size()).isEqualTo(0);
			}
		}

		@Test
		@DisplayName("고객을 전체 조회하고 조회된 고객을 반환한다")
		void 고객을_전체_조회하고_조회된_고객을_반환한다() {

			//given
			Customer customer1 = customerJdbcRepository.save(new Customer("testName1"));
			Customer customer2 = customerJdbcRepository.save(new Customer("testName2"));
			List<Customer> createdVouchers = Arrays.asList(customer1, customer2);

			//when
			List<Customer> findVouchers = customerJdbcRepository.findAll();

			//then
			assertThat(findVouchers.get(0).getCustomerId()).isEqualTo(createdVouchers.get(0).getCustomerId());
			assertThat(findVouchers.get(1).getCustomerId()).isEqualTo(createdVouchers.get(1).getCustomerId());
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class deleteById메서드는 {

		@Test
		@DisplayName("고객 아이디로 고객을 삭제한다")
		void 고객_아이디로_고객을_삭제한다() {

			// given
			Customer createdCustomer = customerJdbcRepository.save(new Customer("testName"));

			// when
			int count = customerJdbcRepository.deleteById(createdCustomer.getCustomerId());

			// then
			assertThat(count).isEqualTo(1);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 아이디가_null이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> customerJdbcRepository.deleteById(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.getMessage());
			}
		}
	}
}

