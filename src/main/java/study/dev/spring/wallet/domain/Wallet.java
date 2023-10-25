package study.dev.spring.wallet.domain;

import java.util.UUID;

public class Wallet {

	private final String uuid;

	private final String customerId;

	private final String voucherId;

	private Wallet(
		String customerId,
		String voucherId
	) {
		this.uuid = UUID.randomUUID().toString();
		this.customerId = customerId;
		this.voucherId = voucherId;
	}

	public Wallet(
		String uuid,
		String customerId,
		String voucherId
	) {
		this.uuid = uuid;
		this.customerId = customerId;
		this.voucherId = voucherId;
	}

	//==Factory method==//
	public static Wallet of(
		String customerId,
		String voucherId
	) {
		return new Wallet(customerId, voucherId);
	}

	//==Utility method==//
	public String getUuid() {
		return uuid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getVoucherId() {
		return voucherId;
	}
}
