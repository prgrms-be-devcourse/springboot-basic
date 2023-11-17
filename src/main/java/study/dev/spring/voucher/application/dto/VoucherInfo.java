package study.dev.spring.voucher.application.dto;

public record VoucherInfo(
	String id,
	String name,
	String voucherType,
	double discountAmount
) {
}
