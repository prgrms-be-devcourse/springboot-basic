package com.programmers.springbasic.repository.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.mapper.CustomerMapper;
import com.programmers.springbasic.utils.FileUtils;

@Repository
@Profile("prod")
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
	public Customer save(Customer customer) {
		return null;
	}

	@Override
	public List<Customer> findAll() {
		return null;
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public List<Customer> findAllByIsBlackListedTrue() {
		List<String> fileLines = fileUtils.readFile(blacklistFilePath);
		return customerMapper.linesToCustomers(fileLines);
	}

	@Override
	public void deleteById(Long id) {

	}

}
