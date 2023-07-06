package co.programmers.voucher_management.voucher.entity;

import java.time.LocalDateTime;

import co.programmers.voucher_management.exception.VoucherReassignmentException;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherUpdateDTO;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Voucher {
	private long id;
	private DiscountStrategy discountStrategy;
	private long customerId;
	private char status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public Voucher(long id, DiscountStrategy discountStrategy, long customerId) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		this.customerId = customerId;
		status = STATUS.NORMAL.symbol;
	}

	@Builder
	public Voucher(long id, DiscountStrategy discountStrategy) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		status = STATUS.NORMAL.symbol;
	}

	public Voucher(VoucherRequestDTO voucherRequestDTO) {
		String discountType = voucherRequestDTO.getDiscountType();
		int discountAmount = voucherRequestDTO.getDiscountAmount();
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		this.discountStrategy = discountStrategy;
	}

	public Voucher(VoucherUpdateDTO voucherUpdateDTO) {
		long id = voucherUpdateDTO.getId();
		String discountType = voucherUpdateDTO.getDiscountType();
		int discountAmount = voucherUpdateDTO.getDiscountAmount();
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		this.discountStrategy = discountStrategy;
		this.id = id;
	}

	public void assignCustomer(long customerId) {
		if (customerId > 0) {
			throw new VoucherReassignmentException("Already assigned voucher");
		}
		this.customerId = customerId;
	}

	public void delete() {
		this.status = STATUS.DELETED.symbol;
	}

	public void changeDiscountType(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
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
