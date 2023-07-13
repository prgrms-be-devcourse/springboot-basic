package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.exception.CantReadFileException;
import com.prgms.springbootbasic.exception.CantWriteFileException;
import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.FormatCSV;
import com.prgms.springbootbasic.util.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@Repository
public class VouchersInFile implements VouchersStorage {

	@Value("${file.voucher}") private String filePath;
	private final FormatCSV formatCSV;

	public VouchersInFile(FormatCSV formatCSV) {
		this.formatCSV = formatCSV;
	}

	@Override
	public void save(Voucher voucher) {
		File file = new File(filePath);
		try {
			Files.write(file.toPath(), formatCSV.changeVoucherToCSV(voucher).getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new CantWriteFileException(ExceptionMessage.CANT_WRITE_FILE);
		}
	}
	
	@Override
	public List<Voucher> findAll() {
		try {
			File file = new File(filePath);
			return Files.readAllLines(file.toPath())
					.stream()
					.map(s -> changeToVoucher(s.split(",")))
					.toList();
		} catch (IOException e) {
			throw new CantReadFileException(ExceptionMessage.CANT_READ_FILE);
		}
	}
	
	private Voucher changeToVoucher(String[] voucherOfCSV) {
		String type = voucherOfCSV[0];
		UUID voucherId = UUID.fromString(voucherOfCSV[1]);
		long number = Long.parseLong(voucherOfCSV[2]);
		VoucherType voucherType = VoucherType.of(type);
		return voucherType.createVoucher(voucherId, number);
	}

}
