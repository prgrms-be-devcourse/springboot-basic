package study.dev.spring.voucher.infrastructure.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherData {

	@CsvBindByName
	private String uuid;
	@CsvBindByName
	private String name;
	@CsvBindByName
	private String voucherType;
	@CsvBindByName
	private double discountAmount;
}
