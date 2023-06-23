package org.prgrms.kdt.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;

@Component
public class InMemoryVoucherRepository implements VoucherRepository {

	private Map<Long, VoucherEntity> map;
	private Long lastIdx;

	public InMemoryVoucherRepository() {
		this.map = new ConcurrentHashMap<>();
		this.lastIdx = 0L;
	}

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		lastIdx += 1;
		voucherEntity.setVoucherId(lastIdx);
		VoucherEntity voucher = saveVoucher(voucherEntity);
		return voucherEntity;
	}

	@Override
	public List<VoucherEntity> readAll() {
		return new ArrayList<>(map.values());
	}

	@Override
	public VoucherEntity saveVoucher(VoucherEntity voucherEntity) {
		map.put(voucherEntity.getVoucherId(), voucherEntity);
		return voucherEntity;
	}
}
