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
public class VoucherData {

	@CsvBindByName
	private UUID uuid;
	@CsvBindByName
	private String name;
	@CsvBindByName
	private String voucherType;
	@CsvBindByName
	private double discountAmount;

	public VoucherData(final Voucher voucher) {
		this.uuid = voucher.getUuid();
		this.name = voucher.getName();
		this.voucherType = voucher.getTypeName();
		this.discountAmount = voucher.getDiscountAmount();
	}

	public Voucher toVoucher() {
		return new Voucher(
			uuid,
			name,
			VoucherType.valueOf(voucherType),
			discountAmount
		);
	}
}
