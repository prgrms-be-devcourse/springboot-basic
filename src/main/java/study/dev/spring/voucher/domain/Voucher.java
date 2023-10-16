package study.dev.spring.voucher.domain;

import java.util.UUID;

public class Voucher {

	private final UUID uuid;

	private String name;

	private VoucherType type;

	private double discountAmount;

	private Voucher(
		final String name,
		final VoucherType type,
		final double discountAmount
	) {
		this.uuid = UUID.randomUUID();
		this.name = name;
		this.type = type;
		this.discountAmount = discountAmount;
	}

	public Voucher(
		final UUID uuid,
		final String name,
		final VoucherType type,
		final double discountAmount
	) {
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.discountAmount = discountAmount;
	}

	//==Factory method==//
	public static Voucher createVoucher(
		final VoucherType voucherType,
		final String name,
		final double discountAmount
	) {
		voucherType.validateDiscountAmount(discountAmount);
		return new Voucher(name, voucherType, discountAmount);
	}

	//==Utility method==//
	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public String getTypeDescription() {
		return type.getDescription();
	}

	public String getTypeName() {
		return type.name();
	}

	public double getDiscountAmount() {
		return discountAmount;
	}
}
