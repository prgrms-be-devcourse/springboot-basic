package org.prgrms.kdt.repository.voucher;

import java.util.List;

import org.prgrms.kdt.model.entity.VoucherEntity;
import org.springframework.stereotype.Component;

@Component

public interface VoucherRepository {

	public VoucherEntity createVoucher(VoucherEntity voucherEntity);

	public List<VoucherEntity> readAll();
}
