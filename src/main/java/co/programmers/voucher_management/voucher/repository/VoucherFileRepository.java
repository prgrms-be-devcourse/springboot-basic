package co.programmers.voucher_management.voucher.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import co.programmers.voucher_management.exception.FileManagementException;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;

@Repository
@Profile("dev")
public class VoucherFileRepository implements VoucherRepository {
	private final String filePath;
	private final Path path;
	private int voucherCount;

	private VoucherFileRepository(@Value(value = "${file.voucher.path}") String filePath) {
		this.filePath = filePath;
		path = Paths.get(filePath);
		voucherCount = getVoucherCount();
	}

	@Override
	public void save(Voucher voucher) {
		String id = String.valueOf(voucher.getId());
		String amount = String.valueOf(voucher.getDiscountStrategy().getAmount());
		String discountType = voucher.getDiscountStrategy().getType();
		String[] parsedVoucherData = {id, amount, discountType};
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true))) {
			csvWriter.writeNext(parsedVoucherData);
			voucherCount++;
		} catch (IOException ioException) {
			throw new FileManagementException("File Writer Failed");
		}
	}

	@Override
	public List<Voucher> findAll() {

		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<Voucher> vouchers = new ArrayList<>();
			int fileLine = voucherCount;
			for (int line = 0; line < fileLine; line++) {
				String[] oneLine = csvReader.readNext();
				vouchers.add(mapToVoucher(oneLine));
			}
			return vouchers;
		} catch (IOException ioException) {
			throw new FileManagementException("File Reader Failed");
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

	@Override
	public int getVoucherCount() {
		try (Reader reader = Files.newBufferedReader(path);
			 CSVReader csvReader = new CSVReader(reader)) {
			List<String[]> vouchers = csvReader.readAll();
			return vouchers.size();
		} catch (IOException ioException) {
			throw new FileManagementException("File Reader Failed");
		}
	}

	public enum VoucherProperty {
		ID(0),
		AMOUNT(1),
		DISCOUNT_TYPE(2);

		final int index;

		VoucherProperty(int index) {
			this.index = index;
		}
	}
}
