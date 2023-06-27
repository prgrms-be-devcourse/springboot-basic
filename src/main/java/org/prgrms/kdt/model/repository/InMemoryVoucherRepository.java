package org.prgrms.kdt.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryVoucherRepository implements VoucherRepository {

	private Map<Long, VoucherEntity> map;

	private IdGenerator idGenerator;

	@Autowired
	public InMemoryVoucherRepository(Map<Long, VoucherEntity> map, IdGenerator idGenerator) {
		this.map = map;
		this.idGenerator = idGenerator;
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
