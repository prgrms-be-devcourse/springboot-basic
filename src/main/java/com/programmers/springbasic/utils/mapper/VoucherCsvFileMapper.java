package com.programmers.springbasic.utils.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Component
public class VoucherCsvFileMapper {

	private static final String DELIMITER = ",";

	public List<String> voucherMapToLines(Map<UUID, Voucher> storage) {
		return storage.entrySet().stream().map(entry -> {
			Voucher voucher = entry.getValue();
			return String.join(DELIMITER,
				entry.getKey().toString(),
				voucher.getVoucherType().toString(),
				String.valueOf(voucher.getDiscountValue()),
				voucher.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		}).toList();
	}

	public Map<UUID, Voucher> linesToVoucherMap(List<String> fileLines) {
		return fileLines.stream()
			.map(line -> {
				String[] parts = line.split(DELIMITER);
				UUID id = UUID.fromString(parts[0]);
				VoucherType type = VoucherType.valueOf(parts[1]);
				LocalDateTime createdAt = LocalDateTime.parse(parts[3],
					DateTimeFormatter.ISO_LOCAL_DATE_TIME); 

				if (type == VoucherType.FIXED_AMOUNT) {
					long amount = Long.parseLong(parts[2]);
					return new FixedAmountVoucher(id, amount, createdAt);
				} else if (type == VoucherType.PERCENT_DISCOUNT) {
					long percent = Long.parseLong(parts[2]);
					return new PercentDiscountVoucher(id, percent, createdAt);
				}
				return null;
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(Voucher::getVoucherId, voucher -> voucher));
	}

}
