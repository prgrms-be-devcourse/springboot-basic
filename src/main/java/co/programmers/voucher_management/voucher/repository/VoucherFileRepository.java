package co.programmers.voucher_management.voucher.repository;

import static co.programmers.voucher_management.customer.entity.Status.*;

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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import co.programmers.voucher_management.voucher.entity.Status;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;

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
		Status status = Status.NORMAL;
		String createdAt = LocalDateTime.now().toString();
		String updatedAt = createdAt;
		String[] dataToWrite = {id, discountType, amount, customerId, status.toString(), createdAt, updatedAt};
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true))) {
			csvWriter.writeNext(dataToWrite);
			voucherCount++;
		} catch (IOException ioException) {
			throw new RuntimeException("file writer failed");
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
			return file.stream()
					.filter(line->line[VoucherProperty.STATUS.index].equals(NORMAL.toString()))
					.map(this::mapToVoucher)
					.collect(Collectors.toList());
		} catch (IOException ioException) {
			throw new RuntimeException("file reader failed");
		}
	}
	Voucher mapToVoucher(String[] fileLine){
		long id = Long.parseLong(fileLine[VoucherProperty.ID.index()]);
		String discountType = fileLine[VoucherProperty.DISCOUNT_TYPE.index()];
		int discountAmount = Integer.parseInt(
				fileLine[VoucherProperty.DISCOUNT_AMOUNT.index()]);
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		long customerId = Long.parseLong(fileLine[VoucherProperty.CUSTOMER_ID.index()]);
		String statusExpression = fileLine[VoucherProperty.STATUS.index()];
		Status status = Status.valueOf(statusExpression);

		return new Voucher(id, discountStrategy, customerId, status);
	}

	@Override
	public Optional<Voucher> findById(long id) {
		String[] fileLine;
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			while ((fileLine = csvReader.readNext()) != null) {
				long idCompared = Long.parseLong(fileLine[VoucherProperty.ID.index]);
				if (idCompared == id) {
					return Optional.of(mapToVoucher(fileLine));
				}
			}
		} catch (IOException ioException) {
			throw new RuntimeException("file reader failed");
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
			throw new RuntimeException("file reader failed");
		}
	}

	private List<Voucher> findByCustomerId(List<String[]> vouchers, long customerId) {
		List<Voucher> foundVouchers = new ArrayList<>();
		for (String[] voucher : vouchers) {
			long CustomerIdCompared = Long.parseLong(voucher[VoucherProperty.CUSTOMER_ID.index]);
			if (CustomerIdCompared == customerId) {
				foundVouchers.add(mapToVoucher(voucher));
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
			throw new RuntimeException("file writer failed");
		}
	}

	public int getVoucherCount() {
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<String[]> vouchers = csvReader.readAll();
			return vouchers.size();
		} catch (IOException ioException) {
			throw new RuntimeException("file reader failed");
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

		public int index() {
			return index;
		}
	}
}
