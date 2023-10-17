package com.programmers.springbasic.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;
import com.programmers.springbasic.enums.ErrorCode;

@Component
public class FileUtils {
	private static final String DELIMITER = ",";
	@Value("${file.blacklist-path}")
	private String blacklistFilePath;
	@Value("${file.voucher-path}")
	private String voucherFilePath;

	public List<Customer> readBlacklistFile() {
		checkFileExistAndCreate(blacklistFilePath);
		try {
			return Files.readAllLines(Paths.get(blacklistFilePath), StandardCharsets.UTF_8).stream()
				.map(this::lineToCustomer)
				.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_READ.getMessage());
		}
	}

	public Map<UUID, Voucher> readVoucherFile() {
		checkFileExistAndCreate(voucherFilePath);
		try {
			return Files.readAllLines(Paths.get(voucherFilePath), StandardCharsets.UTF_8).stream()
				.map(this::lineToVoucher)
				.flatMap(Optional::stream)
				.collect(Collectors.toMap(Voucher::getVoucherId, voucher -> voucher));
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_READ.getMessage());
		}
	}

	public void writeVoucherFile(Map<UUID, Voucher> storage) {
		List<String> lines = storage.entrySet().stream()
			.map(this::voucherToLine)
			.collect(Collectors.toList());

		try {
			Files.write(Paths.get(voucherFilePath), lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE,
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_WRITE.getMessage());
		}
	}

	private void checkFileExistAndCreate(String pathString) {
		Path path = Paths.get(pathString);
		try {
			if (Files.notExists(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			if (Files.notExists(path)) {
				Files.createFile(path);
			}
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_CREATE.getMessage());
		}
	}

	private Customer lineToCustomer(String line) {
		String[] parts = line.split(DELIMITER);
		Long id = Long.parseLong(parts[0]);
		String name = parts[1];
		boolean isBlackListed = Boolean.parseBoolean(parts[2]);
		return new Customer(id, name, isBlackListed);
	}

	private Optional<Voucher> lineToVoucher(String line) {
		String[] parts = line.split(DELIMITER);
		UUID id = UUID.fromString(parts[0]);
		VoucherType type = VoucherType.valueOf(parts[1]);

		if (type == VoucherType.FIXED_AMOUNT) {
			long amount = Long.parseLong(parts[2]);
			return Optional.of(new FixedAmountVoucher(id, amount));
		} else if (type == VoucherType.PERCENT_DISCOUNT) {
			long percent = Long.parseLong(parts[2]);
			return Optional.of(new PercentDiscountVoucher(id, percent));
		}
		return Optional.empty();
	}

	private String voucherToLine(Map.Entry<UUID, Voucher> entry) {
		Voucher voucher = entry.getValue();
		return String.join(DELIMITER,
			entry.getKey().toString(),
			voucher.getVoucherType().toString(),
			String.valueOf(voucher.getDiscountValue()));
	}
}
