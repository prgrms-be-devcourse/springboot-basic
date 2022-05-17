package com.programmers.order.manager;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.VoucherDto;
import com.programmers.order.type.VoucherType;

public interface VoucherManager {

	Voucher publishVoucher();

	VoucherType getType();

	Voucher resolve(VoucherDto.Resolver resolver);

}
