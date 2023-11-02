package study.dev.spring.wallet.application.dto;

public record CreateWalletRequest(
	String customerId,
	String voucherId
) {
}
