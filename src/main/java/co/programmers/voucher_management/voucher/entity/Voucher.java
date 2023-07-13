package co.programmers.voucher_management.voucher.entity;

import co.programmers.voucher_management.exception.VoucherReassignmentException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Voucher {
	private long id;
	private DiscountStrategy discountStrategy;
	private long customerId;
	private Status status;

	@Builder
	public Voucher(long id, DiscountStrategy discountStrategy, long customerId, Status status) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		this.customerId = customerId;
		this.status = status;
	}

	@Builder
	public Voucher(long id, DiscountStrategy discountStrategy) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		status = Status.NORMAL;
	}

	public Voucher(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
		status = Status.NORMAL;
	}

	public void assignCustomer(long customerId) {
		if (this.customerId > 0) {
			throw new VoucherReassignmentException("Already assigned voucher");
		}
		this.customerId = customerId;
	}

	public void delete() {
		this.status = Status.DELETED;
	}

	public void changeDiscountType(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
	}

	public void assignId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Voucher{" +
				"id=" + id +
				", discountType=" + discountStrategy.getType() +
				", discountAmount=" + discountStrategy.getAmount() +
				", customerId=" + customerId +
				", status='" + status.toString() + '\'' +
				'}';
	}
}
