package org.prgms.springbootbasic.voucher.repository.vouchertype;

import java.util.UUID;

import org.prgms.springbootbasic.voucher.vo.VoucherType;

public interface VoucherTypeRepository {

	/**
	 * Voucher 종류, 타입을 저장하는 메서드
	 * @param voucherType
	 * @return
	 */
	VoucherType save(VoucherType voucherType);

	/**
	 * Voucher 이름을 입력받으면 해당 Voucher_type_id를 반환
	 * Voucher의 외래키로 Voucher_type_id를 넣어주는 데 사용하기 위한 의도로 정의
	 *
	 * @param voucherTypeName
	 * @return UUID : voucher_type_id
	 */
	UUID findUUIDByName(String voucherTypeName);

	/**
	 * voucher_type_id를 입력받으면 VoucherType을 반환해주는 메서드
	 *
	 * @param voucherId
	 * @return VoucherType
	 */
	VoucherType findById(UUID voucherId);
}
