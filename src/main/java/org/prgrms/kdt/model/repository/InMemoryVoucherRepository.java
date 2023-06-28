package org.prgrms.kdt.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.prgrms.kdt.model.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

	private Map<Long, Voucher> map;

	@Autowired
	public InMemoryVoucherRepository(Map<Long, Voucher> map) {
		this.map = map;
	}

	@Override
	public Voucher createVoucher(Voucher voucherEntity) {
		Voucher voucher = saveVoucher(voucherEntity);
		return voucherEntity;
	}

	@Override
	public List<Voucher> readAll() {
		return new ArrayList<>(map.values());
	}

	@Override
	public Voucher saveVoucher(Voucher voucher) {
		map.put(voucher.getVoucherId(), voucher);
		return voucher;
	}
}
