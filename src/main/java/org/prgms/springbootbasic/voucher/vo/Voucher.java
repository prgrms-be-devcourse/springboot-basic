package org.prgms.springbootbasic.voucher.vo;

import java.util.UUID;

public interface Voucher {
	UUID getVoucherId();
	long discount(long beforeDiscount);
}
