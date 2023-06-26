package org.prgrms.kdt.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.util.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class InMemoryVoucherRepository implements VoucherRepository {

	private Map<Long, VoucherEntity> map;

	private IdGenerator idGenerator;

	public InMemoryVoucherRepository() {
		this.map = new ConcurrentHashMap<>();
		this.idGenerator = new IdGenerator();
	}

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		Long voucherId = idGenerator.getRandomId();
		voucherEntity.setVoucherId(voucherId);
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
