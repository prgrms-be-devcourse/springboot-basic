package com.programmers.springbasic.repository.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.utils.mapper.CustomerCsvFileMapper;
import com.programmers.springbasic.utils.FileUtils;

@Repository
public class BlacklistCustomerRepository {

	private final CustomerCsvFileMapper customerCsvFileMapper;

	@Value("${file.blacklist-path}")
	private String blacklistFilePath;

	public BlacklistCustomerRepository(CustomerCsvFileMapper customerCsvFileMapper) {
		this.customerCsvFileMapper = customerCsvFileMapper;
	}

	public List<Customer> getBlacklistCustomers() {
		List<String> fileLines = FileUtils.readFile(blacklistFilePath);
		return customerCsvFileMapper.linesToCustomers(fileLines);
	}

}
