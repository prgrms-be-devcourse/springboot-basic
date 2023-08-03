package org.prgrms.kdt.model.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("InMemoryVoucherRepository")
public class InMemoryVoucherRepository implements VoucherRepository {

	private final Map<Long, VoucherEntity> map;

	@Autowired
	public InMemoryVoucherRepository(Map<Long, VoucherEntity> map) {
		this.map = map;
	}

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		map.put(voucherEntity.getVoucherId(), voucherEntity);
		return voucherEntity;
	}

	@Override
	public List<VoucherEntity> findAll() {
		return new ArrayList<>(map.values());
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {
		map.remove(voucherEntity.getVoucherId());
		map.put(voucherEntity.getVoucherId(), voucherEntity);
		return voucherEntity;
	}

	@Override
	public Optional<VoucherEntity> findById(Long voucherId) {
		return Optional.of(map.get(voucherId));
	}

	@Override
	public boolean deleteById(Long voucherId) {
		if (map.remove(voucherId) == null) {
			return false;
		} else {
			return false;
		}
	}
}
