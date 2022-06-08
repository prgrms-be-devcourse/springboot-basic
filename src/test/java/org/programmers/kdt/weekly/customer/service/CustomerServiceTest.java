package org.programmers.kdt.weekly.customer.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.customer.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	private final CustomerRepository customerRepository = mock(CustomerRepository.class);
	private final CustomerService customerService = new CustomerService(customerRepository);
	private final String NAME = "TestName";
	private final String EMAIL = "test@gmail.com";
	private final UUID CUSTOMER_ID = UUID.randomUUID();
	private final Customer CUSTOMER = new Customer(CUSTOMER_ID, EMAIL, NAME, CustomerType.NORMAL);

	@Test
	@DisplayName("create 함수 호출시 customerRepository.insert() 호출 후 customer 반환")
	void create() {
		//given
		when(customerRepository.insert(CUSTOMER)).thenReturn(CUSTOMER);
		//when
		var createCustomer = customerService.create(CUSTOMER_ID, EMAIL, NAME);
		//then
		verify(customerRepository, times(1)).insert(CUSTOMER);
		assertThat(createCustomer, samePropertyValuesAs(CUSTOMER));
	}

	@Test
	@DisplayName("findByCustomerType 함수 호출시 customerRepository.findById() 호출 후 customer list 반환")
	void findByCustomerType() {
		//given
		CustomerType customerType = CustomerType.NORMAL;
		List<Customer> customers = List.of(CUSTOMER);
		when(customerRepository.findByType(customerType.toString())).thenReturn(customers);
		//when
		var findCustomer = customerService.findByCustomerType(customerType);
		//then
		verify(customerRepository, times(1)).findByType(customerType.toString());
		assertThat(findCustomer, equalTo(customers));
	}

	@Test
	@DisplayName("findByEmail 함수 호출시 customerRepository.findByEmail() 호출 후 Optional<customer> 반환")
	void findByEmail() {
		//given
		Optional<Customer> optionalCustomer = Optional.of(CUSTOMER);
		when(customerRepository.findByEmail(EMAIL)).thenReturn(optionalCustomer);
		//when
		var findCustomer = customerService.findByEmail(EMAIL);
		//then
		verify(customerRepository, times(1)).findByEmail(EMAIL);
		assertThat(findCustomer, equalTo(optionalCustomer));
	}

	@Test
	@DisplayName("changeBlackType 함수 호출시 customerRepository() 호출 후 타입 변환된 customer 반환")
	void changeBlackType() {
		//given
		Customer changeCustomer = CUSTOMER.changeCustomerType(CustomerType.BLACK);
		when(customerRepository.update(changeCustomer)).thenReturn(changeCustomer);
		//when
		var updateCustomer = customerService.changeBlackType(CUSTOMER);
		//then
		verify(customerRepository, times(1)).update(changeCustomer);
		assertThat(updateCustomer, samePropertyValuesAs(updateCustomer));
	}
}