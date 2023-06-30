package org.prgrms.kdt.model.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.prgrms.kdt.controller.MainController;
import org.prgrms.kdt.model.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Primary
@Repository
public class FileVoucherRepository implements VoucherRepository{

	private ObjectMapper objectMapper;
	private FileIO fileIO;
	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	public FileVoucherRepository(ObjectMapper objectMapper, FileIO fileIO) {
		this.objectMapper = objectMapper;
		this.fileIO = fileIO;
	}

	@Override
	public Voucher createVoucher(Voucher voucher) {
		return saveVoucher(voucher);
	}

	@Override
	public List<Voucher> readAll() {
		String fileAllText = fileIO.loadStringFromFile();
		List<Voucher> vouchers = null;
		try {
			vouchers = objectMapper.readValue(fileAllText, new TypeReference<List<Voucher>>() {});
		} catch (JsonProcessingException e) {
			logger.error("readAll 메서드에서 파일 불러오기 실패");
			logger.error(e.toString());
		}
		return vouchers;
	}

	@Override
	public Voucher saveVoucher(Voucher voucher) {
		try {
			String voucherJson = objectMapper.writeValueAsString(voucher);
			fileIO.saveStringToFile(voucherJson + System.lineSeparator());
		} catch (JsonProcessingException e) {
			logger.error("save 메서드에서 voucher 저장 실패");
			logger.error(e.toString());
		}

		return voucher;
	}
}
