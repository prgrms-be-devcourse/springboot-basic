package org.prgrms.kdt.model.repository;

import java.util.List;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;

@Component

public interface VoucherRepository {

	public VoucherEntity createVoucher(VoucherEntity voucherEntity);

	public List<VoucherEntity> readAll();

	public VoucherEntity saveVoucher(VoucherEntity voucherEntity);
}
