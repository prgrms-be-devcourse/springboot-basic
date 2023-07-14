package co.programmers.voucher_management.customer.repository;

import static co.programmers.voucher_management.customer.entity.Customer.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;

import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.entity.Status;

@Repository
@Profile("file")
public class CustomerFileRepository implements CustomerRepository {
	private final Path path;

	public CustomerFileRepository(@Value(value = "${file.blacklist.path}") String filePath) {
		path = Paths.get(filePath);
	}

	@Override
	public List<Customer> findByRating(String ratingToFind) {
		int ratingIndex = CustomerProperty.RATING.index;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<String[]> customers = csvReader.readAll();
			return customers.stream()
					.filter(fileLine -> ratingToFind.equals(fileLine[ratingIndex]))
					.map(this::mapToCustomer)
					.collect(Collectors.toList());
		} catch (IOException ioException) {
			throw new RuntimeException("file reading failed");
		}
	}

	Customer mapToCustomer(String[] fileLine) {
		long id = Long.parseLong(fileLine[CustomerProperty.ID.index]);
		String name = fileLine[CustomerProperty.NAME.index];
		String phoneNumber = fileLine[CustomerProperty.PHONE_NUMBER.index];

		String ratingExpression = fileLine[CustomerProperty.RATING.index];
		Rating rating = Rating.valueOf(ratingExpression);
		return new Customer(id, name, rating, phoneNumber);
	}

	@Override
	public Optional<Customer> findById(long customerId) {
		String[] line;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			while ((line = csvReader.readNext()) != null) {
				long id = Long.parseLong(line[CustomerProperty.ID.index]);
				String status = line[CustomerProperty.STATUS.index];
				String normalStatus = String.valueOf(Status.NORMAL);
				if ((customerId == id) && (status.equals(normalStatus))) {
					return Optional.of(mapToCustomer(line));
				}
			}
		} catch (IOException ioException) {
			throw new RuntimeException("file reading failed");
		}
		return Optional.empty();
	}

	public enum CustomerProperty {
		ID(0),
		NAME(1),
		RATING(2),
		PHONE_NUMBER(3),
		STATUS(4);

		final int index;

		CustomerProperty(int index) {
			this.index = index;
		}

		public int index() {
			return index;
		}

	}
}
