package org.prgms.springbootbasic.customer.repository.customerstatus;

import java.util.UUID;

import org.prgms.springbootbasic.customer.entity.CustomerStatus;

public interface CustomerStatusRepository {

	/**
	 * 입력받은 statusId를 통해 CustomerStatus를 반환해주는 메서드
	 * @param statusId
	 * @return
	 */
	CustomerStatus findById(UUID statusId);

	/**
	 * 입력 받은 이름으로 customer_status 식별번호를 얻는 메서드
	 * @param statusName
	 * @return
	 */
	UUID findUUIDByName(String statusName);
}
