package org.prgrms.kdt.model.repository;

import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.model.entity.VoucherEntity;

public interface VoucherRepository {

	VoucherEntity createVoucher(VoucherEntity voucherEntity);

	List<VoucherEntity> findAll();

	VoucherEntity updateVoucher(VoucherEntity voucherEntity);

	VoucherEntity findById(Long voucherId);

	void deleteById(Long voucherId);
}
