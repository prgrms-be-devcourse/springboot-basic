package org.prgms.springbootbasic.voucher.repository;

import java.util.List;

import org.prgms.springbootbasic.voucher.vo.Voucher;

public interface VoucherRepository {

	/**
	 * Voucher를 저장하는 메서드
	 * @param voucher
	 * @return 저장한 Voucher
	 */
	Voucher save(Voucher voucher);

	/**
	 * Voucher 리스트를 조회하는 메서드
	 * @return Voucher 리스트
	 */
	List<Voucher> getVoucherList();
}
