package org.prgrms.springorder.service;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.repository.customer.CustomerRepository;
import org.prgrms.springorder.repository.customer.FileBlackListRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

	private BlackListService blackListService;
	private FileBlackListRepository fileBlackListRepository;

	@BeforeAll
	public void beforeAll() {
		fileBlackListRepository = mock(FileBlackListRepository.class);
		blackListService = new BlackListService(fileBlackListRepository);
	}

	@Test
	@DisplayName("블랙리스트를 받아서 고객을 스트링으로 변환하여 성공적으로 반환한다.")
	void test1() {
		int size = 2;
		List<Customer> customerList = new ArrayList<>();
		Customer customer1 = new Customer(UUID.randomUUID(), "이건우","abce",LocalDateTime.now(), CustomerType.BLACK);
		Customer customer2 = new Customer(UUID.randomUUID(), "블랙독","qwer",LocalDateTime.now(), CustomerType.BLACK);
		customerList.add(customer1);
		customerList.add(customer2);

		List<String> toStringBlackList = customerList.stream()
			.map(Customer::toString).toList();

		when(fileBlackListRepository.findAll()).thenReturn(customerList);

		//when
		List<String> blackList = blackListService.findAll();

		//then
		Assertions.assertNotNull(blackList);
		Assertions.assertEquals(size, blackList.size());
		Assertions.assertLinesMatch(toStringBlackList, blackList);
		verify(fileBlackListRepository).findAll();
	}
}