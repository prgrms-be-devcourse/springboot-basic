package com.prgrms.vouchermanagement.voucher.domain;

public interface Discountable {
	long evaluate(long discountInfo, long beforeDiscount);
}
