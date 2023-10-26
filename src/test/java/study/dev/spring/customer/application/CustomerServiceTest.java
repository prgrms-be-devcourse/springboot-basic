package study.dev.spring.customer.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import study.dev.spring.customer.application.dto.CustomerInfo;
import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;
import study.dev.spring.customer.fixture.CustomerFixture;
import study.dev.spring.wallet.domain.WalletRepository;
import study.dev.spring.wallet.fixture.WalletFixture;

@DisplayName("[CustomerService 테스트] - Application Layer")
class CustomerServiceTest {

	private final CustomerService customerService;
	private final CustomerRepository customerRepository;
	private final WalletRepository walletRepository;

	public CustomerServiceTest() {
		this.customerRepository = Mockito.mock(CustomerRepository.class);
		this.walletRepository = Mockito.mock(WalletRepository.class);
		this.customerService = new CustomerService(walletRepository, customerRepository);
	}

	@Test
	@DisplayName("[특정 바우처를 가진 고객을 모두 조회한다]")
	void getCustomersByVoucher() {
		//given
		String voucherId = "voucherId";
		List<Customer> customers = CustomerFixture.getCustomers();

		given(walletRepository.findByCustomerId(voucherId))
			.willReturn(WalletFixture.getWallets());
		given(customerRepository.findByIds(anyList()))
			.willReturn(customers);

		//when
		List<CustomerInfo> actual = customerService.getCustomersByVoucher(voucherId);

		//then
		assertCustomerInfo(actual, customers);
	}

	private void assertCustomerInfo(List<CustomerInfo> actualList, List<Customer> expectedList) {
		assertThat(actualList).hasSameSizeAs(expectedList);

		for (int i = 0; i < actualList.size(); i++) {
			CustomerInfo actual = actualList.get(i);
			Customer expected = expectedList.get(i);
			assertAll(
				() -> assertThat(actual.id()).isEqualTo(expected.getUuid()),
				() -> assertThat(actual.name()).isEqualTo(expected.getName())
			);
		}
	}
}