package study.dev.spring.voucher.domain;

import java.util.UUID;

import study.dev.spring.voucher.domain.type.VoucherType;

public class Voucher {

	private UUID uuid;

	private String name;

	private VoucherType type;

	private double discountAmount;
}
