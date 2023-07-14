package co.programmers.voucher_management.voucher.dto;

import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherRequestDTO {
	private String discountType;
	private Integer discountAmount;

	public Voucher mapToVoucher() {
		String discountType = getDiscountType();
		int discountAmount = getDiscountAmount();
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		return new Voucher(discountStrategy);
	}
}
