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
	public void deleteById(Long voucherId) {
		VoucherEntity targetVoucher = findById(voucherId).orElseThrow(
			() -> new RuntimeException("존재하지 않기 때문에 삭제할 수 없는 id 입니다.")
		);
		map.remove(targetVoucher.getVoucherId());
	}
}
