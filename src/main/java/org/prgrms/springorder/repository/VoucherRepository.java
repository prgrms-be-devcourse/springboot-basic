package org.prgrms.springorder.repository;

import java.util.List;

import org.prgrms.springorder.domain.Voucher;

public interface VoucherRepository {

	void save(Voucher v);

	List<Voucher> findAll();

}
