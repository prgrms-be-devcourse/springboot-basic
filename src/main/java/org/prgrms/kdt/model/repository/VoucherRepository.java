package org.prgrms.kdt.model.repository;

import java.util.List;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;


public interface VoucherRepository {

	VoucherEntity createVoucher(VoucherEntity voucherEntity);

	List<VoucherEntity> readAll();

	VoucherEntity saveVoucher(VoucherEntity voucherEntity);
}
