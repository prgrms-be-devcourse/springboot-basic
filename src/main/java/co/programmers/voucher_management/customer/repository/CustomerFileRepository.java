package co.programmers.voucher_management.customer.repository;

import static co.programmers.voucher_management.voucher.entity.Voucher.STATUS.*;

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
		if (Customer.Rating.BLACKLIST.name().equals(rating)) {
			return findBlackList();
		}
		return List.of();
	}

	@Override
	public Optional<Customer> findById(long customerId) {
		String[] line;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			while((line = csvReader.readNext())!=null){
				long id = Long.parseLong(line[CustomerProperty.ID.index]);
				String status = line[CustomerProperty.STATUS.index];
				if((customerId == id) && (status.equals(NORMAL.toString()))){
					return Optional.of(new Customer(line));
				}
			}
		}catch (IOException ioException) {
			throw new RuntimeException("File Reader Failed");
		}
		return Optional.empty();
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
		} catch (IOException ioException) {
			throw new RuntimeException("File Reader Failed");
		}
	}

	private Customer maptoCustomer(String[] oneLine) {
		return Customer.builder()
				.name(oneLine[CustomerProperty.NAME.index])
				.build();
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
