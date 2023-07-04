package co.programmers.voucher_management.voucher.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Voucher {
	private int id;
	private final DiscountStrategy discountStrategy;
	private char status;

	public Voucher(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
	}

	@Builder
	public Voucher(int id, DiscountStrategy discountStrategy) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		status = STATUS.NORMAL.symbol;
	}

	public void delete() {
		this.status = STATUS.DELETED.symbol;
	}

	public enum STATUS {
		NORMAL('Y'),
		DELETED('N');
		char symbol;

		STATUS(char symbol) {
			this.symbol = symbol;
		}
	}
}
