package co.programmers.voucher_management.voucher.repository;

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
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;

@Repository
@Profile({"file", "test"})
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
		String amount = String.valueOf(voucher.getDiscountStrategy().getAmount());
		String discountType = voucher.getDiscountStrategy().getType();
		String[] parsedVoucherData = {id, amount, discountType};
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true))) {
			csvWriter.writeNext(parsedVoucherData);
			voucherCount++;
		} catch (IOException ioException) {
			throw new RuntimeException("File Writer Failed");
		}
		return voucher;
	}

	private String assignId() {
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + random.nextInt(1000);
		return id;
	}

	@Override
	public List<Voucher> findAll() {

		try (Reader reader = Files.newBufferedReader(path); CSVReader csvReader = new CSVReader(reader)) {
			List<Voucher> vouchers = new ArrayList<>();
			int fileLine = voucherCount;
			for (int line = 0; line < fileLine; line++) {
				String[] oneLine = csvReader.readNext();
				vouchers.add(mapToVoucher(oneLine));
			}
			return vouchers;
		} catch (IOException ioException) {
			throw new RuntimeException("File Reader Failed");
		}
	}

	@Override
	public void deleteOf(int id) {
		List<Voucher> vouchers = findAll();
		vouchers.stream()
				.filter(voucher -> voucher.getId() == id)
				.forEach(Voucher::delete);

		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, false))) {
		} catch (IOException ioException) {
			throw new RuntimeException("File Writer Failed");
		}
		for (Voucher voucher : vouchers) {
			create(voucher);
		}
	}

	private Voucher mapToVoucher(String[] oneLine) {
		int id = Integer.parseInt(oneLine[VoucherProperty.ID.index]);
		int amount = Integer.parseInt(oneLine[VoucherProperty.AMOUNT.index]);
		String discountType = oneLine[VoucherProperty.DISCOUNT_TYPE.index];
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, amount);
		return Voucher.builder()
				.id(id)
				.discountStrategy(discountStrategy)
				.build();
	}

	public int getVoucherCount() {
		try (Reader reader = Files.newBufferedReader(path); CSVReader csvReader = new CSVReader(reader)) {
			List<String[]> vouchers = csvReader.readAll();
			return vouchers.size();
		} catch (IOException ioException) {
			throw new RuntimeException("File Reader Failed");
		}
	}

	public enum VoucherProperty {
		ID(0), AMOUNT(1), DISCOUNT_TYPE(2);

		final int index;

		VoucherProperty(int index) {
			this.index = index;
		}
	}
}
