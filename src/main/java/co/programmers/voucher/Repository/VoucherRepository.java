package co.programmers.voucher.Repository;

import java.util.List;

import co.programmers.voucher.Voucher.Voucher;

public interface VoucherRepository {

	void save(Voucher requestBody);

	List<Voucher> inquire();
}
