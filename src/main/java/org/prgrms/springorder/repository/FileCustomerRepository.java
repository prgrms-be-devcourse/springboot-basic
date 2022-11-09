package org.prgrms.springorder.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.prgrms.springorder.domain.Customer;
import org.prgrms.springorder.domain.CustomerType;
import org.prgrms.springorder.exception.NoSuchVoucherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	private final Resource resource;

	public FileCustomerRepository(@Value("${repository.file.customer.blacklist.save-path}") String path) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		resource = resourceLoader.getResource(path);

	}

	@Override
	public List<Customer> getBlackList() {

		List<Customer> customerList = new ArrayList<>();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] customerInfo = line.split(",");
				UUID id = UUID.fromString(customerInfo[0]);
				customerList.add(new Customer(id, customerInfo[1]));
			}
			return customerList;

		} catch (IOException e) {
			e.printStackTrace();
			throw new NoSuchVoucherException(e.getMessage());
		}
	}
}

