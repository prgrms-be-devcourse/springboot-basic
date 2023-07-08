package co.programmers.voucher_management.voucher.repository;

import static co.programmers.voucher_management.common.Status.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.voucher.entity.Voucher;

@Repository
@Profile("file")
public class VoucherFileRepository implements VoucherRepository {
	private final String filePath;
	private final Path path;
	private int voucherCount;

	VoucherFileRepository(@Value(value = "${file.voucher.path}") String filePath) {
		this.filePath = filePath;
		path = Paths.get(filePath);
		voucherCount = getVoucherCount();
	}

	@Override
	public Voucher create(Voucher voucher) {
		String id = assignId();
		String discountType = voucher.getDiscountStrategy().getType();
		String amount = String.valueOf(voucher.getDiscountStrategy().getAmount());
		String customerId = "";
		String status = NORMAL.getSymbol();
		String createdAt = LocalDateTime.now().toString();
		String updatedAt = createdAt;
		String[] dataToWrite = {id, discountType, amount, customerId, status, createdAt, updatedAt};
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true))) {
			csvWriter.writeNext(dataToWrite);
			voucherCount++;
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
		return voucher;
	}

	private String assignId() {
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + random.nextInt(1000);
	}

	@Override
	public List<Voucher> findAll() {
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<Voucher> vouchers = new ArrayList<>();
			List<String[]> file = csvReader.readAll();
			for (String[] fileLine : file) {
				String status = fileLine[VoucherProperty.STATUS.index];
				if (NORMAL.getSymbol().equals(status)) {
					vouchers.add(new Voucher(fileLine));
				}
			}
			return vouchers;
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
	}

	@Override
	public Optional<Voucher> findById(long id) {
		String[] fileLine;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			while ((fileLine = csvReader.readNext()) != null) {
				long idCompared = Long.parseLong(fileLine[VoucherProperty.ID.index]);
				if (idCompared == id) {
					return Optional.of(new Voucher(fileLine));
				}
			}
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
		return Optional.empty();
	}

	@Override
	public Voucher update(Voucher voucher) {
		long id = voucher.getId();

		deleteById(id);

		return create(voucher);
	}

	@Override
	public Voucher assignCustomer(Voucher voucher, Customer customer) {
		long id = voucher.getId();
		long customerId = customer.getId();
		deleteById(id);
		voucher.assignCustomer(customerId);
		return voucher;
	}

	@Override
	public List<Voucher> findByCustomerId(long customerId) {
		List<String[]> vouchers;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			vouchers = csvReader.readAll();
			return findByCustomerId(vouchers, customerId);
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
	}

	private List<Voucher> findByCustomerId(List<String[]> vouchers, long customerId) {
		List<Voucher> foundVouchers = new ArrayList<>();
		for (String[] voucher : vouchers) {
			long CustomerIdCompared = Long.parseLong(voucher[VoucherProperty.CUSTOMER_ID.index]);
			if (CustomerIdCompared == customerId) {
				foundVouchers.add(new Voucher(voucher));
			}
		}
		return foundVouchers;
	}

	@Override
	public void deleteById(long id) {
		List<Voucher> vouchers = findAll();
		vouchers.stream()
				.filter(voucher -> voucher.getId() == id)
				.forEach(Voucher::delete);
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, false))) {
			vouchers.stream()
					.map(voucher -> new String[] {
							String.valueOf(voucher.getId()),
							voucher.getDiscountStrategy().getType(),
							String.valueOf(voucher.getDiscountStrategy().getAmount()),
							String.valueOf(voucher.getCustomerId()), String.valueOf(voucher.getStatus())})
					.forEach(csvWriter::writeNext);
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
	}

	public int getVoucherCount() {
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<String[]> vouchers = csvReader.readAll();
			return vouchers.size();
		} catch (IOException ioException) {
			throw new RuntimeException();
		}
	}

	public enum VoucherProperty {
		ID(0),
		DISCOUNT_TYPE(1),
		DISCOUNT_AMOUNT(2),
		CUSTOMER_ID(3),
		STATUS(4);
		final int index;

		VoucherProperty(int index) {
			this.index = index;
		}
	}
}
