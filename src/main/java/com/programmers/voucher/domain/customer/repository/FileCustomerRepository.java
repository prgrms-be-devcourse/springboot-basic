package com.programmers.voucher.domain.customer.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.exception.CustomerNotFoundException;
import com.programmers.voucher.exception.EmptyBufferException;
import com.programmers.voucher.exception.ExceptionMessage;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	private static final Logger log = LoggerFactory.getLogger(FileCustomerRepository.class);
	private static final Map<UUID, Customer> customers = new LinkedHashMap();
	private static final String LINE_SEPARATOR = ", |: ";
	private final String filePath;

	public FileCustomerRepository(@Value("${repository.file.blacklist}") String filePath) {
		this.filePath = filePath;
	}

	@PostConstruct
	void readFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] customerInfo = line.split(LINE_SEPARATOR);
				UUID customerId = UUID.fromString(customerInfo[1]);
				String type = customerInfo[3];
				String createdAt = customerInfo[5];
				String lastModifiedAt = customerInfo[7];

				if (type.equals(CustomerType.BLACKLIST.name())) {
					CustomerType customerType = CustomerType.getCustomerType(type);
					Customer customer = new Customer(customerId,
						LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						customerType,
						LocalDateTime.parse(lastModifiedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					customers.put(customerId, customer);
				}
			}
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new EmptyBufferException();
		}
	}

	@PreDestroy
	void writeFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
			for (Customer customer : customers.values()) {
				writer.write(customer.toString() + System.lineSeparator());
				writer.flush();
			}
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new RuntimeException(ExceptionMessage.EMPTY_BUFFER.getMessage());
		}
	}

	@Override
	public Customer save(Customer customer) {
		customers.put(customer.getCustomerId(), customer);
		return customer;
	}

	@Override
	public Customer findById(UUID customerId) {
		return Optional.ofNullable(customers.get(customerId))
			.orElseThrow(() -> {
				log.error(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
				throw new CustomerNotFoundException();
			});
	}

	@Override
	public Customer update(UUID customerId, Customer updateCustomer) {
		Optional.ofNullable(customers.get(customerId))
			.ifPresentOrElse(customer -> customers.put(customerId, customer),
				() -> {
					log.error(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
					throw new CustomerNotFoundException();
				}
			);

		return updateCustomer;
	}

	@Override
	public void delete(UUID customerId) {
		Optional.ofNullable(customers.get(customerId))
			.ifPresentOrElse(customer -> customers.remove(customerId),
				() -> {
					log.error(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage());
					throw new CustomerNotFoundException();
				}
			);
	}

	@Override
	public List<Customer> findAll() {
		return new ArrayList<>(customers.values());
	}

	@Override
	public List<Customer> findAllBlacklist() {
		return customers.values().stream()
			.filter(customer -> customer.getCustomerType().equals(CustomerType.BLACKLIST))
			.collect(Collectors.toList());
	}

	@Override
	public void clear() {
		customers.clear();
	}
}
