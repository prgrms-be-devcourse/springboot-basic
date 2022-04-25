package com.programmers.order.manager;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.VocuherDto;
import com.programmers.order.type.VoucherType;

public interface VoucherManager {

	Voucher create();

	VoucherType getType();

	Voucher resolve(VocuherDto.Resolver resolver);

}
