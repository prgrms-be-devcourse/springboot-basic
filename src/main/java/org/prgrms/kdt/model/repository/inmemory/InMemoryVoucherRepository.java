package org.prgrms.kdt.model.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.VoucherRuntimeException;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.model.repository.file.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	public VoucherEntity saveVoucher(VoucherEntity voucherEntity) {
		try {
			map.put(voucherEntity.getVoucherId(), voucherEntity);
			return voucherEntity;
		} catch (RuntimeException ex) {
			logger.error("voucher entity id is {}", voucherEntity.getVoucherId());
			logger.error(ErrorCode.VOUCHER_CREATE_FAIL.getErrorMessage(), ex);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_CREATE_FAIL);
		}
	}

	@Override
	public List<VoucherEntity> findAllEntities() {
		return new ArrayList<>(map.values());
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {
		try {
			map.put(voucherEntity.getVoucherId(), voucherEntity);
		} catch (RuntimeException e) {
			logger.error("voucher entity id is {}", voucherEntity.getVoucherId());
			logger.error(ErrorCode.VOUCHER_UPDATE_FAIL.getErrorMessage(), e);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_UPDATE_FAIL);
		}
		return voucherEntity;
	}

	@Override
	public Optional<VoucherEntity> findVoucherById(Long voucherId) {
		VoucherEntity foundVoucher = map.get(voucherId);

		if (foundVoucher != null) {
			return Optional.of(foundVoucher);
		}

		logger.error(ErrorCode.VOUCHER_ID_NOT_FOUND.getErrorMessage());
		throw new VoucherRuntimeException(ErrorCode.VOUCHER_ID_NOT_FOUND);
	}

	@Override
	public void deleteVoucherById(Long voucherId) {
		try {
			map.remove(voucherId);
		} catch (RuntimeException e) {
			logger.error("voucher entity id is {}", voucherId);
			logger.error(ErrorCode.VOUCHER_DELETE_FAIL.getErrorMessage(), e);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_DELETE_FAIL);
		}
	}
}
