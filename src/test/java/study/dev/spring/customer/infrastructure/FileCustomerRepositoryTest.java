package study.dev.spring.customer.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import study.dev.spring.common.stub.FileUtilsStub;
import study.dev.spring.customer.domain.Customer;

@DisplayName("[FileCustomerRepository Test] - Infrastructure Layer")
class FileCustomerRepositoryTest {

	private final FileCustomerRepository fileCustomerRepository = new FileCustomerRepository(
		List.of(new FileUtilsStub()), "path"
	);

	@Test
	@DisplayName("[모든 유저를 조회한다]")
	void findAllTest() {
		//when
		List<Customer> actual = fileCustomerRepository.findAll();

		//then -> save 로직이 없기 때문에 비어있는지만 검사
		assertThat(actual).isEmpty();
	}
}