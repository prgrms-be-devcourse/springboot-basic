package org.prgms.springbootbasic.voucher.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public class FileVoucherRepository implements VoucherRepository{
	@Override
	public UUID save(Voucher voucher) {
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

	@Override
	public Voucher findById(UUID percentStoredId) {
		return null;
	}

}
