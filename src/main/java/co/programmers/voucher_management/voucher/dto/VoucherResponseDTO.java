package co.programmers.voucher_management.voucher.dto;

import java.text.MessageFormat;

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

	@Override
	public String toString() {
		return MessageFormat.format(
				"ID : {0}\n"
						+ "Discount Type : {1}\n"
						+ "Discount Amount : {2}\n", id, discountType, discountAmount);
	}
}
