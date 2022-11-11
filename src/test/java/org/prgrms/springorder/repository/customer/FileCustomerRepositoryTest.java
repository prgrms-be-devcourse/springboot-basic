package org.prgrms.springorder.repository.customer;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.properties.GlobalProperties;

class FileCustomerRepositoryTest {

	private FileCustomerRepository fileCustomerRepository;

	private final String voucherPath = "./src/test/resources/voucher_list.csv";
	private final String blackListPath = "./src/test/resources/customer_blacklist.csv";

	@BeforeEach
	void init() {
		GlobalProperties globalProperties = new GlobalProperties(voucherPath, blackListPath);
		fileCustomerRepository = new FileCustomerRepository(globalProperties);
	}

	@Test
	@DisplayName("파일에 저장된 바우처를 로컬 저장소에 성공적으로 가져온다.")
	void test3() {
		//given
		int expectSize = 1;
		fileCustomerRepository.loadBlackList();
		//when
		List<Customer> blackList = fileCustomerRepository.getBlackList();
		//then
		Assertions.assertEquals(expectSize, blackList.size());
	}

}