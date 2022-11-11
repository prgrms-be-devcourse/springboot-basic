package org.prgrms.springorder.repository.customer;

import java.util.List;

import org.prgrms.springorder.domain.customer.Customer;

public interface CustomerRepository {

	List<Customer> getBlackList();

}
