package co.programmers.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class VoucherRequestDTO {
	private String discountType;
	private Integer discountAmount;
	private String discountStrategy;
}
