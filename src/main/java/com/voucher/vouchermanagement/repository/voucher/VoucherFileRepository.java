package com.voucher.vouchermanagement.repository.voucher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.voucher.vouchermanagement.controller.api.v1.VoucherCriteria;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.utils.deserializer.CsvMapper;
import com.voucher.vouchermanagement.utils.deserializer.VoucherCsvMapper;
import com.voucher.vouchermanagement.utils.file.FileIOUtils;

public class VoucherFileRepository implements VoucherRepository {
	private final Resource voucherDb;
	private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
	private final CsvMapper<Voucher> csvMapper = new VoucherCsvMapper();

	public VoucherFileRepository(@Value("${db.file.path}") String dbDirectory,
		@Value("${db.file.voucher.name}") String voucherDbName, ResourceLoader resourceLoader) {
		voucherDb = resourceLoader.getResource(dbDirectory + voucherDbName);
	}

	@Override
	public void insert(Voucher voucher) {
		try {
			String voucherCsvData = this.csvMapper.serialize(voucher);
			FileIOUtils.writeln(this.voucherDb.getFile(), voucherCsvData, true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	@Override
	public List<Voucher> findAll() {
		try {
			return FileIOUtils.readAllLine(voucherDb.getFile())
				.stream()
				.map(csvMapper::deserialize)
				.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());
		}

		return Collections.emptyList();
	}

	@Override
	public List<Voucher> findByType(VoucherType type) {
		return null;
	}

	@Override
	public List<Voucher> findByDate(LocalDateTime startAt, LocalDateTime endAt) {
		return null;
	}

	@Override
	public List<Voucher> findByTypeAndDate(VoucherType type, LocalDateTime startAt, LocalDateTime endAt) {
		return null;
	}

	@Override
	public void deleteById(UUID id) {

	}

	public void deleteAll() {
		try {
			FileIOUtils.writeln(voucherDb.getFile(), null, false);
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		try {
			return FileIOUtils.readAllLine(voucherDb.getFile())
				.stream()
				.map(csvMapper::deserialize)
				.filter(v -> v.getVoucherId() == id)
				.findFirst();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());
		}

		return Optional.empty();
	}

	@Override
	public Voucher update(Voucher voucher) {
		return null;
	}
}
