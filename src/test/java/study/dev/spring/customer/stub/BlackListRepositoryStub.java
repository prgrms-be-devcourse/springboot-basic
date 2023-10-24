package study.dev.spring.customer.stub;

import java.util.List;
import java.util.UUID;

import study.dev.spring.customer.application.BlackListRepository;
import study.dev.spring.customer.application.dto.CustomerData;

public class BlackListRepositoryStub implements BlackListRepository {

	@Override
	public List<CustomerData> findAll() {
		return List.of(new CustomerData(UUID.randomUUID().toString(), "customer"));
	}
}
