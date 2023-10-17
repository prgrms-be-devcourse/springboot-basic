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

import com.programmers.springbasic.enums.ErrorCode;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Component
public class VoucherFileUtils {

	@Value("${file.voucher-path}")
	private String voucherFilePath;

	public Map<UUID, Voucher> readFile() {
		checkFileExistAndCreate();
		try {
			return Files.readAllLines(Paths.get(voucherFilePath), StandardCharsets.UTF_8).stream()
				.map(this::lineToVoucher)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(Voucher::getVoucherId, voucher -> voucher));
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_READ.getMessage());
		}
	}

	private void checkFileExistAndCreate() {
		Path path = Paths.get(voucherFilePath);
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

	private Optional<Voucher> lineToVoucher(String line) {
		String[] parts = line.split(",");
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

	public void writeFile(Map<UUID, Voucher> storage) {
		List<String> lines = storage.entrySet().stream()
			.map(this::voucherToLine)
			.collect(Collectors.toList());

		try {
			Files.write(Paths.get(voucherFilePath), lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_WRITE.getMessage());
		}
	}

	private String voucherToLine(Map.Entry<UUID, Voucher> entry) {
		Voucher voucher = entry.getValue();
		return String.join(",",
			entry.getKey().toString(),
			voucher.getVoucherType().toString(),
			String.valueOf(voucher.getDiscountValue()));
	}


}
