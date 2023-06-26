package co.programmers.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class VoucherInquiryResponseDTO {
	private int id;
	private String name;
	private String description;
	private String discountType;
	private Integer discountAmount;
	private String createdAt;
	private String expiredAt;

}
