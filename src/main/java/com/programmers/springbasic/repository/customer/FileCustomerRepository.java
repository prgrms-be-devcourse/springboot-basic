package com.programmers.springbasic.repository.customer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.enums.ErrorCode;
import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.repository.customer.CustomerRepository;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	@Value("${file.blacklist-path}")
	private String blacklistFilePath;

	@Override
	public List<Customer> findAllByIsBlackListedTrue() {
		checkFileExistAndCreate();
		try {
			return Files.readAllLines(Paths.get(blacklistFilePath), StandardCharsets.UTF_8).stream()
				.map(this::lineToCustomer)
				.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_READ.getMessage());
		}
	}

	private void checkFileExistAndCreate() {
		Path path = Paths.get(blacklistFilePath);
		try {
			if (Files.notExists(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			if (Files.notExists(path)) {
				Files.createFile(path);
			}
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_CREATE.getMessage());
		}
	}

	private Customer lineToCustomer(String line) {
		String[] parts = line.split(",");
		Long id = Long.parseLong(parts[0]);
		String name = parts[1];
		boolean isBlackListed = Boolean.parseBoolean(parts[2]);
		return new Customer(id, name, isBlackListed);
	}
}

