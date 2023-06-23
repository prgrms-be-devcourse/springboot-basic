package org.prgrms.kdt.repository.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.prgrms.kdt.model.entity.VoucherEntity;

public class InMemoryVoucherRepository implements VoucherRepository {

	private Map<Long, VoucherEntity> map;

	public InMemoryVoucherRepository() {
		this.map = new ConcurrentHashMap<>();
	}

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		map.put(voucherEntity.getVoucherId(), voucherEntity);
		return voucherEntity;
	}

	@Override
	public List<VoucherEntity> readAll() {
		return new ArrayList<>(map.values());
	}
}
