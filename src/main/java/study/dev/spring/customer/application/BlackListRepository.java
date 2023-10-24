package study.dev.spring.customer.application;

import java.util.List;

import study.dev.spring.customer.application.dto.CustomerData;

public interface BlackListRepository {

	List<CustomerData> findAll();
}
