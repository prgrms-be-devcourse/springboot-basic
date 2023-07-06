package co.programmers.voucher_management.voucher.dto;

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
}
