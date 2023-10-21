package com.programmers.springbasic.repository.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.mapper.CustomerMapper;
import com.programmers.springbasic.utils.FileUtils;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	private final FileUtils fileUtils;
	private final CustomerMapper customerMapper;

	@Value("${file.blacklist-path}")
	private String blacklistFilePath;

	public FileCustomerRepository(FileUtils fileUtils, CustomerMapper customerMapper) {
		this.fileUtils = fileUtils;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<Customer> findAllByIsBlackListedTrue() {
		List<String> fileLines = fileUtils.readFile(blacklistFilePath);
		return customerMapper.linesToCustomers(fileLines);
	}

}
