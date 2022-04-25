package org.prgms.springbootbasic.wallet.repository;

import java.util.UUID;

import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.prgms.springbootbasic.wallet.entity.VoucherStatus;

public interface VoucherStatusRepository {

	/**
	 * 입력받은 statusId를 통해 VoucherStatus를 반환해주는 메서드
	 * @param statusId
	 * @return
	 */
	VoucherStatus findById(UUID statusId);

	/**
	 * 입력 받은 이름으로 VoucherStatus의 식별번호를 얻는 메서드
	 * @param statusName
	 * @return
	 */
	UUID findUUIDByName(String statusName);
}
