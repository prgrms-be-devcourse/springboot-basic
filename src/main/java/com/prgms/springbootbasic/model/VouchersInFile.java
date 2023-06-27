package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.util.VoucherApplication;
import com.prgms.springbootbasic.util.VoucherType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

public class VouchersInFile implements VouchersStorage {

	private final File file;

	public VouchersInFile() throws IOException {
		VoucherApplication voucherApplication = new VoucherApplication();
		this.file = voucherApplication.getFile();
	}

	@Override
	public void save(Voucher voucher) throws IOException {
		Files.write(file.toPath(), voucher.formatOfCSV(), StandardOpenOption.APPEND);
	}
	
	@Override
	public List<Voucher> findAll() throws IOException {
		return Files.readAllLines(file.toPath())
										.stream()
										.map(s -> changeToVoucher(s.split(",")))
										.toList();
	}
	
	private Voucher changeToVoucher(String[] voucherOfCSV) {
		String type = voucherOfCSV[0];
		UUID voucherId = UUID.fromString(voucherOfCSV[1]);
		long number = Long.parseLong(voucherOfCSV[2]);
		VoucherType voucherType = VoucherType.of(type);
		return voucherType.exsistVoucher(voucherId, number);
	}

}
