package co.programmers.voucher_management.voucher.dto;

import java.text.MessageFormat;

import co.programmers.voucher_management.voucher.entity.Voucher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VoucherResponseDTO {
	private final long id;
	private final String discountType;
	private final Integer discountAmount;
	private final long customer_id;

	@Builder
	public VoucherResponseDTO(Voucher voucher) {
		id = voucher.getId();
		discountType = voucher.getDiscountStrategy().getType();
		discountAmount = voucher.getDiscountStrategy().getAmount();
		customer_id = voucher.getCustomerId();
	}

	@Override
	public String toString() {
		return MessageFormat.format("id : {0}, discount type : {1}, discount amount : {2}, assigned customer id : {3}",
				id, discountType, discountAmount, customer_id);
	}
}
