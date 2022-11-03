package com.programmers.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherFactory;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {

	private final String FILE_PATH = "src/main/resources/voucher.csv";
	private final BufferedWriter writer;
	private final VoucherFactory factory;

	public FileVoucherRepository(VoucherFactory factory) throws IOException {
		this.writer = new BufferedWriter(new FileWriter(FILE_PATH, true));
		this.factory = factory;
	}

	@Override
	public void save(Voucher voucher) {
		try {
			writer.write(voucher.toString() + System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
		}
	}

	@Override
	public Voucher findByUUID(UUID voucherId) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(voucherId.toString(), 4)) {
					String[] voucherInfo = line.split(", |: |%");
					String type = voucherInfo[3];
					int discount = Integer.parseInt(voucherInfo[5]);
					return factory.makeVoucher(type, voucherId, discount);
				}
			}
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	public List<Voucher> findAll() {
		List<Voucher> vouchers = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] voucherInfo = line.split(", |: |%");
				UUID id = UUID.fromString(voucherInfo[1]);
				String type = voucherInfo[3];
				int discount = Integer.parseInt(voucherInfo[5]);
				Voucher voucher = factory.makeVoucher(type, id, discount);
				vouchers.add(voucher);
			}
		} catch (IOException e) {
		}

		return vouchers;
	}

	@Override
	public void clear() {

	}
}
