package com.programmers.springbasic.mapper;

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
	public List<String> voucherMapToLines(Map<UUID, Voucher> storage) {
		return storage.entrySet().stream().map(entry -> {
			Voucher voucher = entry.getValue();
			return String.join(",",
				entry.getKey().toString(),
				voucher.getVoucherType().toString(),
				String.valueOf(voucher.getDiscountValue()));
		}).collect(Collectors.toList());
	}

	public Map<UUID, Voucher> linesToVoucherMap(List<String> fileLines) {
		return fileLines.stream()
			.map(line -> {
				String[] parts = line.split(",");
				UUID id = UUID.fromString(parts[0]);
				VoucherType type = VoucherType.valueOf(parts[1]);

				if (type == VoucherType.FIXED_AMOUNT) {
					long amount = Long.parseLong(parts[2]);
					return new FixedAmountVoucher(id, amount);
				} else if (type == VoucherType.PERCENT_DISCOUNT) {
					long percent = Long.parseLong(parts[2]);
					return new PercentDiscountVoucher(id, percent);
				}
				return null;
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toMap(Voucher::getVoucherId, voucher -> voucher));
	}

}
