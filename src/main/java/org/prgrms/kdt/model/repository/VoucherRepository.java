package org.prgrms.kdt.model.repository;

import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.model.entity.VoucherEntity;

public interface VoucherRepository {

	VoucherEntity saveVoucher(VoucherEntity voucherEntity);

	List<VoucherEntity> findAllEntities();

	VoucherEntity updateVoucher(VoucherEntity voucherEntity);

	Optional<VoucherEntity> findVoucherById(Long voucherId);

	void deleteVoucherById(Long voucherId);
}
