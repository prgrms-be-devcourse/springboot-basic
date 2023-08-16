package org.prgrms.kdt.util;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.domain.Voucher;

public abstract class VoucherFactory {

	public abstract Voucher createVoucher(Long id, int amount, VoucherType voucherType);
}
