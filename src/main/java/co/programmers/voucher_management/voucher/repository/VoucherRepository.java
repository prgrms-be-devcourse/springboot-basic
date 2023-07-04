package co.programmers.voucher_management.voucher.repository;

import java.util.List;

import co.programmers.voucher_management.voucher.entity.Voucher;

public interface VoucherRepository {

	Voucher create(Voucher voucher);

	List<Voucher> findAll();

	void deleteOf(int id);
}
