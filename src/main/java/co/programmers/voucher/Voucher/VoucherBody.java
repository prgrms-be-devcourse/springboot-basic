package co.programmers.voucher.Voucher;

import java.util.HashMap;
import java.util.Map;

public class VoucherBody {
	private static final Map<String, Object> parametersWithExplanation = new HashMap<>(){{
		put("name", "");
		put("description", "");
		put("amount","");
		put("type", "Fixed(for FixedAmountVoucher) or Percent(for PercentDiscountVoucher)");

	}};

	private VoucherBody(){}

	public static Map<String, Object> get(){
		return parametersWithExplanation;
	}
}
