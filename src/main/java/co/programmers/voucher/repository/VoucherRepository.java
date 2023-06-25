package co.programmers.voucher.repository;

import java.util.List;
import java.util.Map;

import co.programmers.voucher.entity.Voucher;

public interface VoucherRepository {

	void save(Voucher voucher);

	List<Map<String, Object>> inquire();
}
