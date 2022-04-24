package org.prgms.springbootbasic.customer.repository.customer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.prgms.springbootbasic.customer.entity.Customer;
import org.prgms.springbootbasic.customer.entity.CustomerStatus;

public interface CustomerRepository {

	/**
	 * Customer를 저장하는 메서드
	 *
	 * @param customer
	 * @return UUID 저장한 Customer 식별번호
	 *
	 */
	UUID save(Customer customer);

	/**
	 * Id에 맞는 Customer 반환하는 메서드
	 *
	 * @param customerId
	 * @return Customer
	 */
	Customer findById(UUID customerId);

	/**
	 * Customer 리스트를 CustomerStatus 종류에 따 조회하는 메서드
	 *
	 * @return Map<CustomerStatus, List < Customer>>
	 */
	Map<CustomerStatus, List<Customer>> getCustomerListByStatus();

	/**
	 * 저장된 Customer의 총 인원수를 반환하는 메서드
	 *
	 * @return customer 인원
	 */
	int getTotalCustomerCount();

	/**
	 * 전체 customer 삭제하는 메서드
	 */
	void deleteCustomers();

}
