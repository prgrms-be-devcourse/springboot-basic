package com.programmers.order.manager;

import com.programmers.order.domain.Voucher;
import com.programmers.order.type.VoucherType;

public interface VoucherManager {

	Voucher create();

	VoucherType getType();

}
