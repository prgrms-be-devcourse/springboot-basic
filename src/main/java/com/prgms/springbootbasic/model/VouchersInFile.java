package com.prgms.springbootbasic.model;

import com.prgms.springbootbasic.exception.CantReadFileException;
import com.prgms.springbootbasic.exception.CantWriteFileException;
import com.prgms.springbootbasic.util.ExceptionMessage;
import com.prgms.springbootbasic.util.VoucherApplication;
import com.prgms.springbootbasic.util.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@Repository
public class VouchersInFile implements VouchersStorage {

	private static final String FILE_PATH = "data.csv";
	private final Logger logger = LoggerFactory.getLogger(VouchersInFile.class);

	@Override
	public void save(Voucher voucher) throws IOException {
		File file = VoucherApplication.file(FILE_PATH);
		try {
			Files.write(file.toPath(), voucher.formatOfCSV().getBytes(), )
		} catch (IOException e) {
			logger.error("파일을 쓸 수 없습니다. file path : {}", file.toPath());
			throw new CantWriteFileException(ExceptionMessage.CANT_WRITE_FILE);
		}
	}
	
	@Override
	public List<Voucher> findAll() throws IOException {
		try {
			File file = VoucherApplication.file(FILE_PATH);
			return Files.readAllLines(file.toPath())
					.stream()
					.map(s -> changeToVoucher(s.split(",")))
					.toList();
		} catch (IOException e) {
			logger.error("파일을 읽을 수 없습니다. file path : {}", FILE_PATH);
			throw new CantReadFileException(ExceptionMessage.CANT_READ_FILE);
		}
	}
	
	private Voucher changeToVoucher(String[] voucherOfCSV) {
		String type = voucherOfCSV[0];
		UUID voucherId = UUID.fromString(voucherOfCSV[1]);
		long number = Long.parseLong(voucherOfCSV[2]);
		VoucherType voucherType = VoucherType.of(type);
		return voucherType.exsistVoucher(voucherId, number);
	}

}
