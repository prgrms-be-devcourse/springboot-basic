package com.programmers.springbasic.repository.customer;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.utils.FileUtils;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	private final FileUtils fileUtils;

	public FileCustomerRepository(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}

	@Override
	public List<Customer> findAllByIsBlackListedTrue() {
		return fileUtils.readBlacklistFile();
	}

}

