package study.dev.spring.voucher.application.dto;

public record CreateVoucherRequest(
	String name,
	String voucherType,
	double discountAmount
) {
}
