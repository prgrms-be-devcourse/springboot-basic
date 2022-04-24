package org.prgms.springbootbasic.voucher.repository.vouchertype;

import java.util.UUID;

import org.prgms.springbootbasic.voucher.vo.VoucherType;

public interface VoucherTypeRepository {
	VoucherType save(VoucherType voucherType);
	UUID findUUIDByName(String voucherTypeName);
	VoucherType findById(UUID voucherId);
}
