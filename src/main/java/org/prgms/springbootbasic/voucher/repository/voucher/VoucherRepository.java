package org.prgms.springbootbasic.voucher.repository.voucher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgms.springbootbasic.voucher.entity.VoucherType;
import org.prgms.springbootbasic.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository {

	/**
	 * Voucher를 저장하는 메서드
	 * @param voucher
	 * @return 저장한 Voucher
	 */
	UUID save(Voucher voucher);

	/**
	 * Id에 맞는 Voucher를 반환하는 메서드
	 *
	 * @param voucherId
	 * @return
	 */
	Voucher findById(UUID voucherId);

	/**
	 * Voucher 리스트를 Voucher의 종류에 따 조회하는 메서드
	 *
	 * @return Map<String, List < Voucher>>
	 */
	Map<VoucherType, List<Voucher>> getVoucherListByType();

	/**
	 * 저장된 Voucher의 총 개수를 반환하는 메서드
	 *
	 * @return Voucher의 개수
	 */
	int getTotalVoucherCount();

	/**
	 * Voucher를 삭제하는 메서드
	 */
	void deleteVouchers();

}
