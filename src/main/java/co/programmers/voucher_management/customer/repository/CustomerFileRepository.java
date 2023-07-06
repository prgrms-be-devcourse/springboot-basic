package co.programmers.voucher_management.customer.repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import com.opencsv.CSVReader;

import co.programmers.voucher_management.customer.entity.Customer;

@Profile("file")
public class CustomerFileRepository {
	private final Path path;

	public CustomerFileRepository(@Value(value = "${file.blacklist.path}") String filePath) {
		path = Paths.get(filePath);
	}

	public List<Customer> findByRating(String rating) {
		if (Customer.Rating.BLACKLIST.name().equals(rating)) {
			return findBlackList();
		}
		return List.of();
	}

	private List<Customer> findBlackList() {
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<Customer> customers = new ArrayList<>();
			List<String[]> blackLists = csvReader.readAll();
			for (String[] blackList : blackLists) {
				customers.add(maptoCustomer(blackList));
			}
			return customers;
		} catch (IOException e) {
			throw new RuntimeException("File Reader Failed");
		}
	}

	private Customer maptoCustomer(String[] oneLine) {
		return Customer.builder()
				.name(oneLine[CustomerProperty.Name.index])
				.build();
	}

	public enum CustomerProperty {
		Name(0);

		final int index;

		CustomerProperty(int index) {
			this.index = index;
		}
	}
}
