package org.prgrms.kdt.model.repository;

import java.util.List;

import org.prgrms.kdt.model.entity.VoucherEntity;

public interface VoucherRepository {

	VoucherEntity createVoucher(VoucherEntity voucherEntity);

	List<VoucherEntity> readAll();

	VoucherEntity saveVoucher(VoucherEntity voucherEntity);
}
