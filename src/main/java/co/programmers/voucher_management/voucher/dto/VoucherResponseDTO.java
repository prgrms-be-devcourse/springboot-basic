package co.programmers.voucher_management.voucher.dto;

import java.text.MessageFormat;

import co.programmers.voucher_management.voucher.entity.Voucher;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoucherResponseDTO {
	private int id;
	private String discountType;
	private Integer discountAmount;

	public VoucherResponseDTO(Voucher voucher) {
		id = voucher.getId();
		discountType = voucher.getDiscountStrategy().getType();
		discountAmount = voucher.getDiscountStrategy().getAmount();
	}

	@Override
	public String toString() {
		return MessageFormat.format(
				"ID : {0}\n"
						+ "Discount Type : {1}\n"
						+ "Discount Amount : {2}\n", id, discountType, discountAmount);
	}
}
