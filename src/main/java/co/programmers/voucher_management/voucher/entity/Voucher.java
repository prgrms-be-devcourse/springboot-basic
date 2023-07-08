package co.programmers.voucher_management.voucher.entity;

import co.programmers.voucher_management.common.Status;
import co.programmers.voucher_management.exception.VoucherReassignmentException;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherUpdateDTO;
import co.programmers.voucher_management.voucher.repository.VoucherFileRepository;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Voucher {
	private long id;
	private DiscountStrategy discountStrategy;
	private long customerId;
	private String status;

	@Builder
	public Voucher(long id, DiscountStrategy discountStrategy, long customerId, String status) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		this.customerId = customerId;
		this.status = status;
	}

	@Builder
	public Voucher(long id, DiscountStrategy discountStrategy) {
		this.id = id;
		this.discountStrategy = discountStrategy;
		status = Status.NORMAL.getSymbol();
	}

	public Voucher(VoucherRequestDTO voucherRequestDTO) {
		String discountType = voucherRequestDTO.getDiscountType();
		int discountAmount = voucherRequestDTO.getDiscountAmount();
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		status = Status.NORMAL.getSymbol();
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

	public Voucher(String[] fileLine) {
		this.id = Long.parseLong(fileLine[VoucherFileRepository.VoucherProperty.ID.ordinal()]);
		String discountType = fileLine[VoucherFileRepository.VoucherProperty.DISCOUNT_TYPE.ordinal()];
		int discountAmount = Integer.parseInt(
				fileLine[VoucherFileRepository.VoucherProperty.DISCOUNT_AMOUNT.ordinal()]);
		this.discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		try {
			this.customerId = Long.parseLong(fileLine[VoucherFileRepository.VoucherProperty.CUSTOMER_ID.ordinal()]);
		} catch (NumberFormatException numberFormatException) {
			customerId = 0;
		}
		this.status = fileLine[VoucherFileRepository.VoucherProperty.STATUS.ordinal()];
	}

	public void assignCustomer(long customerId) {
		if (this.customerId > 0) {
			throw new VoucherReassignmentException("Already assigned voucher");
		}
		this.customerId = customerId;
	}

	public void delete() {
		this.status = Status.DELETED.getSymbol();
	}

	public void changeDiscountType(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
	}

	public void assignId(long id) {
		this.id = id;
	}
}
