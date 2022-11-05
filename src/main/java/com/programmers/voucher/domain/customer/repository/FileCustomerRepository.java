package com.programmers.voucher.domain.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	private final String FILE_PATH = "src/main/resources/customer_blacklist.csv";

	@Override
	public List<Customer> findAllBlacklist() {
		List<Customer> customers = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] customerInfo = line.split(", |: ");
				UUID id = UUID.fromString(customerInfo[1]);
				String type = customerInfo[3];

				if (type.equals(CustomerType.BLACKLIST.name())) {
					CustomerType customerType = CustomerType.getCustomerType(type);
					Customer customer = new Customer(id, customerType);
					customers.add(customer);
				}
			}
		} catch (IOException e) {
		}

		return customers;
	}
}
