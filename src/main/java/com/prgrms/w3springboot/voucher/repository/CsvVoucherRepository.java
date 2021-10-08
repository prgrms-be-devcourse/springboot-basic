package com.prgrms.w3springboot.voucher.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherFactory;
import com.prgrms.w3springboot.voucher.VoucherType;

@Repository
@Profile("test")
public class CsvVoucherRepository implements VoucherRepository {
	private static final Logger logger = LoggerFactory.getLogger(CsvVoucherRepository.class);
	private static final Path FILE_PATH = Paths.get(System.getProperty("user.dir"), "voucher", "voucher.csv");
	private static final String DELIMITER = ",";
	private static final int INDEX_UUID = 0;
	private static final int INDEX_AMOUNT = 1;
	private static final int INDEX_TYPE = 2;
	private static final Map<UUID, Voucher> STORAGE = new ConcurrentHashMap<>();

	private final VoucherFactory VOUCHER_FACTORY = VoucherFactory.getInstance();

	@PostConstruct
	public void postConstruct() {
		checkFileExist();

		try {
			List<String> lines = Files.readAllLines(FILE_PATH);
			for (var line : lines) {
				String[] voucherInfo = line.split(DELIMITER);
				UUID csvVoucherId = UUID.fromString(voucherInfo[INDEX_UUID]);
				long discountAmount = Long.parseLong(voucherInfo[INDEX_AMOUNT]);
				VoucherType voucherType = VoucherType.of(voucherInfo[INDEX_TYPE].toLowerCase());

				STORAGE.put(csvVoucherId,
					VOUCHER_FACTORY.createVoucher(csvVoucherId, discountAmount, voucherType, LocalDateTime.now()));
			}
		} catch (IOException e) {
			logger.error("{} - 파일 읽기 중 오류가 발생했습니다.", e.getMessage());
		}
	}

	@PreDestroy
	public void preDestroy() {
		checkFileExist();

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH.toFile()))) {
			StringBuffer sb = new StringBuffer();
			for (Voucher voucher : STORAGE.values()) {
				sb.append(voucher.getVoucherId());
				sb.append(",");
				sb.append(voucher.getAmount());
				sb.append(",");
				sb.append(voucher.getVoucherType());
				sb.append(System.lineSeparator());
				bufferedWriter.write(String.valueOf(sb));
				sb.setLength(0);
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			logger.error("파일 쓰기 중 오류가 발생했습니다. - {}", e.getMessage());
		}
	}

	@Override
	public Optional<Voucher> findById(UUID voucherId) {
		return Optional.ofNullable(STORAGE.get(voucherId));
	}

	@Override
	public Voucher insert(Voucher voucher) {
		STORAGE.put(voucher.getVoucherId(), voucher);
		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		STORAGE.replace(voucher.getVoucherId(), voucher);

		return voucher;
	}

	@Override
	public void delete(UUID voucherId) {
		STORAGE.remove(voucherId);
	}

	@Override
	public List<Voucher> findAll() {
		return new ArrayList<>(STORAGE.values());
	}

	private void checkFileExist() {
		if (!Files.exists(FILE_PATH)) {
			makeFile();
		}
	}

	private void makeFile() {
		try {
			Files.createDirectory(FILE_PATH.getParent());
			Files.createFile(FILE_PATH);
		} catch (IOException e) {
			logger.error("상위 디렉토리가 존재하지 않습니다. - {}", e.getMessage());
		}
	}
}
