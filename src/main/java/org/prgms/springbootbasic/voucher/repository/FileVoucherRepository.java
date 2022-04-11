package org.prgms.springbootbasic.voucher.repository;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.voucher.vo.Voucher;

public class FileVoucherRepository implements VoucherRepository{
	@Override
	public Voucher save(Voucher voucher) {
		return null;
	}

	@Override
	public Map<String,List<Voucher>> getVoucherListByType() {
		return null;
	}

	@Override
	public int getTotalVoucherCount() {
		return 0;
	}

}
