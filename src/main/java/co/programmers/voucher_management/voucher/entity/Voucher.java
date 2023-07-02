package co.programmers.voucher_management.voucher.entity;

import lombok.Builder;

public class Voucher {
	private String id;
	private final DiscountStrategy discountStrategy;
	public Voucher(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
	}
	@Builder
	public Voucher(String id, DiscountStrategy discountStrategy) {
		this.id = id;
		this.discountStrategy = discountStrategy;
	}

	public String getId() {
		return id;
	}

	public DiscountStrategy getDiscountStrategy() {
		return discountStrategy;
	}
}
