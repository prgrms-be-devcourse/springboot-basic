package org.prgms.springbootbasic.voucher.repository;

import java.util.List;

import org.prgms.springbootbasic.voucher.vo.Voucher;

public interface VoucherRepository {
	Voucher save(Voucher voucher);

	List<Voucher> getVoucherList();
}
