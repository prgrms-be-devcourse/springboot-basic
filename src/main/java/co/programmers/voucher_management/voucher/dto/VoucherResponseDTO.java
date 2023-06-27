package co.programmers.voucher_management.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class VoucherResponseDTO {
	private int id;
	private String discountType;
	private Integer discountAmount;
}
