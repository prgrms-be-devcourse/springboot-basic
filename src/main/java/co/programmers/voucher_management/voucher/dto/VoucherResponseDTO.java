package co.programmers.voucher_management.voucher.dto;

import java.text.MessageFormat;

import co.programmers.voucher_management.voucher.entity.Voucher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VoucherResponseDTO {
	private String id;
	private String discountType;
	private Integer discountAmount;

	@Builder
	public VoucherResponseDTO(Voucher voucher) {
		id = voucher.getId();
		discountType = voucher.getDiscountStrategy().getType();
		discountAmount = voucher.getDiscountStrategy().getAmount();
	}

	@Override
	public String toString() {
		return MessageFormat.format("id : {0},discount type : {1},discount amount : {2}",
				id, discountType, discountAmount);
	}
}
