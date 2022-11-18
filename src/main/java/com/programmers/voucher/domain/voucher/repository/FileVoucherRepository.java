package com.programmers.voucher.domain.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.exception.EmptyBufferException;
import com.programmers.voucher.exception.ExceptionMessage;
import com.programmers.voucher.exception.VoucherNotFoundException;

@Repository
@Profile({"dev", "test"})
public class FileVoucherRepository implements VoucherRepository {

	private static final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);
	private static final Map<UUID, Voucher> vouchers = new LinkedHashMap<>();
	private static final String LINE_SEPARATOR = ", |: |%";
	private final String filePath;

	@Autowired
	public FileVoucherRepository(@Value("${repository.file.voucher}") String filePath) {
		this.filePath = filePath;
	}

	@PostConstruct
	void readFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] voucherInfo = line.split(LINE_SEPARATOR);
				UUID voucherId = UUID.fromString(voucherInfo[1]);
				VoucherType type = VoucherType.getVoucherType(voucherInfo[3]);
				String discount = voucherInfo[5];
				Voucher voucher = new Voucher(voucherId, type, discount);
				vouchers.put(voucherId, voucher);
			}
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new EmptyBufferException();
		}
	}

	@PreDestroy
	void writeFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
			for (Voucher voucher : vouchers.values()) {
				writer.write(voucher.toString() + System.lineSeparator());
				writer.flush();
			}
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new EmptyBufferException();
		}
	}

	@Override
	public void save(Voucher voucher) {
		vouchers.put(voucher.getVoucherId(), voucher);
	}

	@Override
	public Voucher findByUUID(UUID voucherId) {
		return Optional.ofNullable(vouchers.get(voucherId))
			.orElseThrow(() -> {
				log.error(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
				throw new VoucherNotFoundException();
			});
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(vouchers.values());
	}

	@Override
	public void clear() {
		vouchers.clear();
	}
}