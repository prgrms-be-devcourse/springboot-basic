package org.prgms.springbootbasic.voucher.repository.voucher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.prgms.springbootbasic.voucher.vo.Voucher;

public interface VoucherRepository {

	/**
	 * Voucher를 저장하는 메서드
	 * @param voucher
	 * @return 저장한 Voucher
	 */
	UUID save(Voucher voucher);

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
	 * Id에 맞는 Voucher를 반환하는 메서드
	 *
	 * @param percentStoredId
	 * @return
	 */
	Voucher findById(UUID percentStoredId);
}
