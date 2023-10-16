package study.dev.spring.voucher.infrastructure.dto;

import java.util.UUID;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherInfo {

	@CsvBindByName
	private UUID uuid;
	@CsvBindByName
	private String name;
	@CsvBindByName
	private VoucherType voucherType;
	@CsvBindByName
	private double discountAmount;

	public Voucher toVoucher() {
		return new Voucher(
			uuid,
			name,
			voucherType,
			discountAmount
		);
	}
}
