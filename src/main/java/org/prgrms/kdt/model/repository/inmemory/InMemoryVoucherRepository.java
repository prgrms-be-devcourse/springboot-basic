package org.prgrms.kdt.model.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.CommonRuntimeException;
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
	public VoucherEntity findById(Long voucherId) {
		VoucherEntity foundVoucher = map.get(voucherId);

		if (foundVoucher != null) {
			return foundVoucher;
		}

		throw new CommonRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
	}

	@Override
	public void deleteById(Long voucherId) {
		try {
			VoucherEntity targetVoucher = findById(voucherId);
			map.remove(targetVoucher.getVoucherId());
		} catch (RuntimeException e) {
			throw new CommonRuntimeException(ErrorCode.VOUCHER_DELETE_FAIL);
		}
	}
}
