package org.prgrms.springorder.repository;

import java.util.List;

import org.prgrms.springorder.domain.Customer;

public interface CustomerRepository {

	List<Customer> getBlackList();


}
