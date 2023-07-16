package co.programmers.voucher_management.voucher.dto;

import java.time.LocalDate;

public record VoucherSearchDTO(
		LocalDate startDate,
		LocalDate endDate,
		String discountType
) {
}
