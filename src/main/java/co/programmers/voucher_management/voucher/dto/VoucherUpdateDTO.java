package co.programmers.voucher_management.voucher.dto;

import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class VoucherUpdateDTO {
	private long id;
	private String discountType;
	private Integer discountAmount;

	public Voucher mapToVoucher(){
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		return new Voucher(id, discountStrategy);
	}
}
