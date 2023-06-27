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

import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.entity.Voucher;

@Repository
@Profile("dev")
public class FileRepository implements VoucherRepository {
	private final String filePath;
	private final int voucherCnt;
	private final Path path;

	private FileRepository(@Value(value = "${file.path}") String filePath) throws IOException {
		this.filePath = filePath;
		path = Paths.get(filePath);
		voucherCnt = getVoucherCount();
	}

	@Override
	public void save(Voucher voucher) throws IOException {
		String id = String.valueOf(voucher.getId());
		String amount = String.valueOf(voucher.getDiscountStrategy().getAmount());
		String discountType = voucher.getDiscountStrategy().getType();
		String[] parsedVoucherData = {id, amount, discountType};
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, true))) {
			csvWriter.writeNext(parsedVoucherData);
		}
	}

	@Override
	public List<VoucherResponseDTO> findAll() throws Exception {
		Reader reader = Files.newBufferedReader(path);
		try (CSVReader csvReader = new CSVReader(reader)) {
			List<VoucherResponseDTO> vouchers = new ArrayList<>();
			int fileLine = voucherCnt;
			for (int line = 0; line < fileLine; line++) {
				String[] voucher = csvReader.readNext();
				VoucherResponseDTO voucherResponseDTO = processToResponseFormat(voucher);
				vouchers.add(voucherResponseDTO);
			}
			return vouchers;
		}
	}

	@Override
	public int getVoucherCount() throws IOException {
		Reader reader = Files.newBufferedReader(path);
		try (CSVReader csvReader = new CSVReader(reader)) {
			List<String[]> vouchers = csvReader.readAll();
			return vouchers.size();
		}
	}

	private VoucherResponseDTO processToResponseFormat(String[] voucher) {
		int id = Integer.parseInt(voucher[VoucherProperty.ID.index]);
		int amount = Integer.parseInt(voucher[VoucherProperty.AMOUNT.index]);
		String discountType = voucher[VoucherProperty.DISCOUNT_TYPE.index];
		return VoucherResponseDTO.builder()
				.id(id)
				.discountType(discountType)
				.discountAmount(amount)
				.build();
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
