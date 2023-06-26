package co.programmers.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherCreationRequestDTO {
	private String name;
	private String description;
	private String discountType;
	private Integer discountAmount;
	private String discountStrategy;
}
