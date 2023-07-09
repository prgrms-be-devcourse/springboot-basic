package co.programmers.voucher_management.customer.repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;

import co.programmers.voucher_management.common.Status;
import co.programmers.voucher_management.customer.entity.Customer;

@Repository
@Profile("file")
public class CustomerFileRepository implements CustomerRepository {
	private final Path path;

	public CustomerFileRepository(@Value(value = "${file.blacklist.path}") String filePath) {
		path = Paths.get(filePath);
	}

	@Override
	public List<Customer> findByRating(String rating) {
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<Customer> foundResult = new ArrayList<>();
			List<String[]> customers = csvReader.readAll();
			for (String[] fileLine : customers) {
				String ratingToCompare = fileLine[CustomerFileRepository.CustomerProperty.RATING.ordinal()];
				if (rating.equals(ratingToCompare)) {
					foundResult.add(new Customer(fileLine));
				}
			}
			return foundResult;
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
	}

	@Override
	public Optional<Customer> findById(long customerId) {
		String[] line;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			while ((line = csvReader.readNext()) != null) {
				long id = Long.parseLong(line[CustomerProperty.ID.index]);
				String status = line[CustomerProperty.STATUS.index];
				String normalStatus = String.valueOf(Status.NORMAL.getSymbol());
				if ((customerId == id) && (status.equals(normalStatus))) {
					return Optional.of(new Customer(line));
				}
			}
		} catch (IOException ioException) {
			throw new RuntimeException();
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

	}
}
