package study.dev.spring.voucher.application.dto;

public record VoucherInfo(
	String name,
	String voucherType,
	double discountAmount
) {
}
