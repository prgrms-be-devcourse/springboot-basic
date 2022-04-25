package org.prgms.springbootbasic.customer.service;

import java.util.List;
import java.util.Map;

import org.prgms.springbootbasic.customer.entity.Customer;
import org.prgms.springbootbasic.customer.entity.CustomerStatus;

public interface CustomerService {
	/**
	 * Voucher의 실물을 생성하고 저장하는 메서드
	 *
	 * @param name
	 * @param email
	 */

	void createCustomer(String name, String email);

	/**
	 * Customer 목록 조회
	 *
	 * @return Map<CustomerStatus, Customer> 바우처 종류 별 목록
	 * */
	Map<CustomerStatus, List<Customer>> list();
}
