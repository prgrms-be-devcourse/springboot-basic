package org.prgrms.kdt.customer.repository;

import java.util.List;
import org.prgrms.kdt.customer.model.Customer;

public interface BlackListRepository {

    List<Customer> findAll();

}
