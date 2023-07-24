package org.prgrms.kdt.model.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.CommonRuntimeException;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.model.repository.file.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		try {
			map.put(voucherEntity.getVoucherId(), voucherEntity);
			return voucherEntity;
		} catch (RuntimeException ex) {
			logger.error(ErrorCode.VOUCHER_CREATE_FAIL.getErrorMessage(), ex);
			throw new CommonRuntimeException(ErrorCode.VOUCHER_CREATE_FAIL);
		}
	}

	@Override
	public List<VoucherEntity> findAll() {
		return new ArrayList<>(map.values());
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {
		try {
			VoucherEntity targetVoucher = findById(voucherEntity.getVoucherId());
			map.put(voucherEntity.getVoucherId(), voucherEntity);
		} catch (RuntimeException e) {
			logger.error(ErrorCode.VOUCHER_UPDATE_FAIL.getErrorMessage(), e);
			throw new CommonRuntimeException(ErrorCode.VOUCHER_UPDATE_FAIL);
		}
		return voucherEntity;
	}

	@Override
	public VoucherEntity findById(Long voucherId) {
		VoucherEntity foundVoucher = map.get(voucherId);

		if (foundVoucher != null) {
			return foundVoucher;
		}

		logger.error(ErrorCode.VOUCHER_ID_NOT_FOUND.getErrorMessage());
		throw new CommonRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
	}

	@Override
	public void deleteById(Long voucherId) {
		try {
			VoucherEntity targetVoucher = findById(voucherId);
			map.remove(targetVoucher.getVoucherId());
		} catch (RuntimeException e) {
			logger.error(ErrorCode.VOUCHER_DELETE_FAIL.getErrorMessage(), e);
			throw new CommonRuntimeException(ErrorCode.VOUCHER_DELETE_FAIL);
		}
	}
}
