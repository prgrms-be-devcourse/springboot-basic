package org.prgrms.kdt.model.repository;

import java.util.List;

import org.prgrms.kdt.model.entity.Voucher;

public interface VoucherRepository {

	Voucher createVoucher(Voucher voucher);

	List<Voucher> readAll();

	Voucher saveVoucher(Voucher voucher);
}
