package com.programmers.order.domain;

import static com.programmers.order.domain.constraint.VoucherConstraint.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.order.dto.VoucherDto;
import com.programmers.order.exception.DomainException;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.VoucherType;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId;
	private final long percent;
	private final LocalDateTime createdAt;

	private PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
		this.voucherId = voucherId;
		this.percent = percent;
		this.createdAt = createdAt;
	}

	private PercentDiscountVoucher(UUID voucherId, long percent) {
		if (PERCENT_VOUCHER.isViolate(percent)) {
			throw new DomainException.ConstraintException(ErrorMessage.CLIENT_ERROR);
		}
		this.voucherId = voucherId;
		this.percent = percent;
		this.createdAt = LocalDateTime.now();
	}

	public static PercentDiscountVoucher create(long percent) {
		return new PercentDiscountVoucher(UUID.randomUUID(), percent);
	}

	public static PercentDiscountVoucher build(VoucherDto.Resolver resolver) {
		return new PercentDiscountVoucher(resolver.getId(), resolver.getDiscountValue(), resolver.getCreatedAt());
	}

	@Override
	public UUID getVoucherId() {
		return voucherId;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String getVoucherType() {
		return VoucherType.PERCENT_VOUCHER.name();
	}

	@Override
	public String getDiscountValue() {
		return String.valueOf(this.percent);
	}

	@Override
	public String show() {
		return "voucherId=" + voucherId +
				", voucherType='" + this.getVoucherType() + '\'' +
				", discountValue='" + this.percent + '\'' +
				", createdAt=" + createdAt;
	}

	@Override
	public long discount(long beforeDiscount) {
		return (long)(beforeDiscount * (1 - (double)percent / 100));
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
				"voucherId=" + voucherId +
				", percent=" + percent +
				", created=" + createdAt +
				'}';
	}
}
