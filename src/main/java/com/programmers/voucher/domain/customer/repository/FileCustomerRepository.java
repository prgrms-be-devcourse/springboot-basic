package com.programmers.voucher.domain.customer.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
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

	@Override
	public List<Customer> findAllBlacklist() {
		return new ArrayList<>(customers.values());
	}

	@PostConstruct
	void setBlacklist() {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] customerInfo = line.split(LINE_SEPARATOR);
				UUID customerId = UUID.fromString(customerInfo[1]);
				String type = customerInfo[3];
				String createdAt = customerInfo[5];
				String modifiedAt = customerInfo[7];

				if (type.equals(CustomerType.BLACKLIST.name())) {
					CustomerType customerType = CustomerType.getCustomerType(type);
					Customer customer = new Customer(customerId,
						LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						customerType,
						LocalDateTime.parse(modifiedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					customers.put(customerId, customer);
				}
			}
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new EmptyBufferException();
		}
	}
}
